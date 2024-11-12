package org.example.microserviciomonopatin.service;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.microserviciomonopatin.exception.MonopatinNotFoundException;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.shareddto.DTO.entity.MonopatinDTO;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;
    @Autowired
    private MonopatinMapper monopatinMapper;


    public Monopatin save(MonopatinDTO monopatinDTO) {
        monopatinDTO.validate();
        Monopatin monopatin = monopatinMapper.mapToEntity(monopatinDTO);
        return monopatinRepository.save(monopatin);
    }


    public MonopatinDTO findById(Long id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new MonopatinNotFoundException("Monopatín no encontrado con ID: " + id));

        return monopatinMapper.mapToDTO(monopatin);
    }

    public MonopatinDTO update(Long id, MonopatinDTO monopatinDTO) {
        try {
            System.out.println("Iniciando actualización del monopatín con ID: " + id);

            // Buscar el monopatín en la base de datos
            Monopatin monopatin = monopatinRepository.findById(id)
                    .orElseThrow(() -> new MonopatinNotFoundException("Monopatín no encontrado con ID: " + id));

            boolean updated = false;

            // Compara y actualiza el campo 'modelo' si es necesario
            if (!monopatin.getModelo().equals(monopatinDTO.getModelo())) {
                monopatin.setModelo(monopatinDTO.getModelo());
                updated = true;
            }

            // Actualiza la latitud
            if (monopatin.getLatitud() != monopatinDTO.getLatitud()) {
                monopatin.setLatitud(monopatinDTO.getLatitud());
                updated = true;
            }

            // Actualiza la longitud
            if (monopatin.getLongitud() != monopatinDTO.getLongitud()) {
                monopatin.setLongitud(monopatinDTO.getLongitud());
                updated = true;
            }

            // Actualiza el estado
            if (!monopatin.getEstado().equals(monopatinDTO.getEstado())) {
                monopatin.setEstado(monopatinDTO.getEstado());
                updated = true;
            }

            // Actualiza el tiempo de uso con pausa
            if (monopatin.getTiempoUsoConPausa() != monopatinDTO.getTiempoUsoConPausa()) {
                monopatin.setTiempoUsoConPausa(monopatinDTO.getTiempoUsoConPausa());
                updated = true;
            }

            // Actualiza el tiempo de uso sin pausa
            if (monopatin.getTiempoUsoSinPausa() != monopatinDTO.getTiempoUsoSinPausa()) {
                monopatin.setTiempoUsoSinPausa(monopatinDTO.getTiempoUsoSinPausa());
                updated = true;
            }

            // Actualiza los km recorridos
            if (monopatin.getTotalKmRecorridos() != monopatinDTO.getTotalKmRecorridos()) {
                monopatin.setTotalKmRecorridos(monopatinDTO.getTotalKmRecorridos());
                updated = true;
            }

            // Actualiza el campo 'disponible' si ha cambiado
            if (monopatin.isDisponible() != monopatinDTO.isDisponible()) {
                monopatin.setDisponible(monopatinDTO.isDisponible());
                updated = true;
            }

            // Si no hubo ningún cambio, no actualizamos el monopatín
            if (!updated) {
                System.out.println("No se encontraron cambios en el monopatín.");
                throw new IllegalArgumentException("No se encontraron cambios en el monopatín.");
            }

            // Guarda el monopatín actualizado
            Monopatin updatedMonopatin = monopatinRepository.save(monopatin);

            System.out.println("Monopatín actualizado con éxito.");

            // Devuelve el DTO actualizado
            return monopatinMapper.mapToDTO(updatedMonopatin);
        } catch (Exception e) {
            // Log de error para detectar detalles de la excepción
            System.out.println("Error en la actualización del monopatín: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la actualización del monopatín", e);
        }
    }



    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    public List<Monopatin> saveAll(List<MonopatinDTO> monopatinDTOs) {
        monopatinDTOs.forEach(MonopatinDTO::validate);  // Validamos cada uno de los DTOs

        List<Monopatin> monopatines = monopatinDTOs.stream()
                .map(monopatinMapper::mapToEntity)
                .collect(Collectors.toList());

        return monopatinRepository.saveAll(monopatines);
    }

    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return monopatines.stream()
                .map(monopatinMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public boolean existeMonopatin(Long monopatinId) {
        return monopatinRepository.existsById(monopatinId);
    }

    public List<ReporteUsoDTO> generarReporteUso() {
        return monopatinRepository.findAllReporteUso();  // Devolvemos todos los reportes
    }

    // Generar reporte por kilómetros recorridos
    public List<ReporteUsoDTO> generarReportePorKilometros() {
        return monopatinRepository.obtenerReporteKm();  // Devolvemos reporte por kilometraje
    }

    // Generar reporte por tiempos con o sin pausa
    public List<ReporteUsoDTO> generarReporteTiempos(boolean excludeSinPausa) {
        return monopatinRepository.obtenerReporteTiempos(excludeSinPausa);
    }

    public Map<String, Long> obtenerCantidadMonopatinesPorEstado() {

        long enOperacion = monopatinRepository.countByEstado("en-operacion");
        long enMantenimiento = monopatinRepository.countByEstado("en-mantenimiento");

        Map<String, Long> resultado = new HashMap<>();
        resultado.put("en_operacion", enOperacion);
        resultado.put("en_mantenimiento", enMantenimiento);

        return resultado;
    }
}
