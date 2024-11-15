package org.example.microserviciomantenimiento.service;

import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microserviciomantenimiento.entity.Mantenimiento;
import org.example.microserviciomantenimiento.foreinClient.MonopatinClient;
import org.example.microserviciomantenimiento.repository.MantenimientoRepository;
import org.example.shareddto.DTO.entity.MantenimientoDTO;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MantenimientoMapper mantenimientoMapper;

    @Autowired
    private MonopatinClient monopatinClient;



    public MantenimientoDTO registrarMantenimiento(MantenimientoDTO mantenimientoDTO) {
        Mantenimiento mantenimiento = mantenimientoMapper.toEntity(mantenimientoDTO);
        Mantenimiento mantenimientoGuardado = mantenimientoRepository.save(mantenimiento);

        ResponseEntity<MonopatinDTO> monopatinResponse = monopatinClient.getMonopatinById(mantenimientoDTO.getMonopatinId());

        if (monopatinResponse.getStatusCode().is2xxSuccessful() && monopatinResponse.getBody() != null) {
            MonopatinDTO monopatin = monopatinResponse.getBody();
            monopatin.setEstado("en-mantenimiento");
            monopatin.setDisponible(false);
            monopatinClient.updateMonopatin(monopatin.getId(), monopatin);

        } else {
            throw new RuntimeException("Monopatín no encontrado.");
        }
            return mantenimientoMapper.toDTO(mantenimientoGuardado);
    }


    public MantenimientoDTO obtenerMantenimientoPorId(Long id) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado con ID: " + id));

        return mantenimientoMapper.toDTO(mantenimiento);
    }


    public MantenimientoDTO actualizarMantenimiento(Long id, MantenimientoDTO mantenimientoDTO) {
        Mantenimiento mantenimientoExistente = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado con ID: " + id));

        mantenimientoExistente.setMonopatinId(mantenimientoDTO.getMonopatinId());
        mantenimientoExistente.setFechaInicioMantenimiento(mantenimientoDTO.getFechaInicioMantenimiento());
        mantenimientoExistente.setFechaFinMantenimiento(mantenimientoDTO.getFechaFinMantenimiento());
        mantenimientoExistente.setDescripcion(mantenimientoDTO.getDescripcion());

        Mantenimiento mantenimientoActualizado = mantenimientoRepository.save(mantenimientoExistente);

        return mantenimientoMapper.toDTO(mantenimientoActualizado);
    }

    public void eliminarMantenimiento(Long id) {
       Mantenimiento mantenimientoExistente = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado con ID: " + id));
        ResponseEntity<MonopatinDTO> monopatinResponse = monopatinClient.getMonopatinById(mantenimientoExistente.getMonopatinId());

        if (monopatinResponse.getStatusCode().is2xxSuccessful() && monopatinResponse.getBody() != null) {
            MonopatinDTO monopatin = monopatinResponse.getBody();
            monopatin.setDisponible(true);
            monopatinClient.updateMonopatin(monopatin.getId(), monopatin);
        } else {
            throw new RuntimeException("Monopatín no encontrado.");
        }
        mantenimientoRepository.delete(mantenimientoExistente);
    }


    public List<MantenimientoDTO> obtenerTodosLosMantenimientos() {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAll();
        if (mantenimientos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron mantenimientos.");
        }
        return mantenimientos.stream()
                .map(mantenimientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Punto a- Generar reporte por tiempos con o sin pausa
    public List<ReporteUsoDTO> generarReporteTiempos(boolean excludeSinOausa) {
        boolean includeSinPausa = !excludeSinOausa;
        ResponseEntity<List<ReporteUsoDTO>> response = monopatinClient.obtenerReporteUsoConTiempos(includeSinPausa);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new RuntimeException("Error al obtener el reporte de uso de los monopatines.");
    }

    public void actualizarEstadoMonopatinesEnMantenimiento(Boolean includeSinPausa, Double maxKm) {
        try {
            ResponseEntity<List<ReporteUsoDTO>> response = monopatinClient.obtenerReporteUsoConTiempos(includeSinPausa);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<ReporteUsoDTO> reportesDeUso = response.getBody();

                for (ReporteUsoDTO reporte : reportesDeUso) {
                    if (reporte.getTotalKmRecorridos() > maxKm) {
                        ResponseEntity<MonopatinDTO> monopatinResponse = monopatinClient.getMonopatinById(reporte.getMonopatinId());

                        if (monopatinResponse.getStatusCode().is2xxSuccessful() && monopatinResponse.getBody() != null) {
                            MonopatinDTO monopatin = monopatinResponse.getBody();

                            monopatin.setEstado("en-mantenimiento");
                            monopatin.setDisponible(false);
                            monopatinClient.updateMonopatin(monopatin.getId(), monopatin);
                            // Crear un nuevo registro de mantenimiento
                            MantenimientoDTO mantenimientoDTO = new MantenimientoDTO();
                            mantenimientoDTO.setMonopatinId(monopatin.getId());
                            mantenimientoDTO.setFechaInicioMantenimiento(LocalDateTime.now());  // Fecha actual
                            mantenimientoDTO.setDescripcion("Mantenimiento programado por exceder el límite de kilómetros");
                            registrarMantenimiento(mantenimientoDTO);

                        } else {

                            throw new RuntimeException("Monopatín con ID " + reporte.getMonopatinId() + " no encontrado.");
                        }
                    }
                }
            } else {
                throw new RuntimeException("No se pudo obtener el reporte de uso de los monopatines.");
            }
        } catch (Exception e) {
            // Log de error
            System.err.println("Error en actualizarEstadoMonopatinesEnMantenimiento: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el estado de los monopatines: " + e.getMessage());
        }
    }

    // Lista de mantenimientos realizados a un Monopatín
    public List<MantenimientoDTO> consultarMantenimientosPorMonopatin(Long monopatinId) {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findByMonopatinId(monopatinId);

        return mantenimientos.stream()
                .map(mantenimientoMapper::toDTO)
                .collect(Collectors.toList());
    }



}