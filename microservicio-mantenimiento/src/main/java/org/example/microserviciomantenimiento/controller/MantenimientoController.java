package org.example.microserviciomantenimiento.controller;

import org.example.microserviciomantenimiento.service.MantenimientoService;
import org.example.shareddto.DTO.entity.MantenimientoDTO;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;


    // Endpoint para registrar un nuevo mantenimiento
    @PostMapping
    public ResponseEntity<MantenimientoDTO> registrarMantenimiento(@RequestBody MantenimientoDTO mantenimientoDTO) {
        MantenimientoDTO mantenimientoGuardado = mantenimientoService.registrarMantenimiento(mantenimientoDTO);
        return new ResponseEntity<>(mantenimientoGuardado, HttpStatus.CREATED);
    }

    // Obtener mantenimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> obtenerMantenimientoPorId(@PathVariable Long id) {
        MantenimientoDTO mantenimientoDTO = mantenimientoService.obtenerMantenimientoPorId(id);
        return ResponseEntity.ok(mantenimientoDTO);
    }

    // Actualizar mantenimiento
    @PutMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> actualizarMantenimiento(@PathVariable Long id,
                                                                    @RequestBody MantenimientoDTO mantenimientoDTO) {
        MantenimientoDTO mantenimientoActualizado = mantenimientoService.actualizarMantenimiento(id, mantenimientoDTO);
        return ResponseEntity.ok(mantenimientoActualizado);
    }

    // Eliminar mantenimiento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMantenimiento(@PathVariable Long id) {
        mantenimientoService.eliminarMantenimiento(id);
        return ResponseEntity.noContent().build();
    }

    //Endpoint para obtener todos los mantenimientos
    @GetMapping
    public ResponseEntity<List<MantenimientoDTO>> obtenerTodosLosMantenimientos() {
        List<MantenimientoDTO> mantenimientos = mantenimientoService.obtenerTodosLosMantenimientos();
        return ResponseEntity.ok(mantenimientos);
    }

    //Punto a-Endpoint para generar reporte de uso de monopatines con o sin pausa
    @GetMapping("/reporteUso-monopatines")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteTiempos(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa) {

        boolean excludeSinPausa = !includeSinPausa;
        List<ReporteUsoDTO> reportes = mantenimientoService.generarReporteTiempos(excludeSinPausa);
        return ResponseEntity.ok(reportes);
    }

    // Endpoint para actualizar el estado de los monopatines en mantenimiento
    @PutMapping("/actualizar-estado")
    public ResponseEntity<Void> actualizarEstadoMonopatinesEnMantenimiento(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa,
            @RequestParam(value = "maxKm", required = true) Double maxKm) {
        try {
            mantenimientoService.actualizarEstadoMonopatinesEnMantenimiento(includeSinPausa, maxKm);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para consultar los mantenimientos de un monopatín
    @GetMapping("/monopatin/{monopatinId}/mantenimientos")
    public ResponseEntity<List<MantenimientoDTO>> consultarMantenimientosPorMonopatin(@PathVariable Long monopatinId) {
        List<MantenimientoDTO> mantenimientosDTO = mantenimientoService.consultarMantenimientosPorMonopatin(monopatinId);
        return ResponseEntity.ok(mantenimientosDTO);
    }
}
