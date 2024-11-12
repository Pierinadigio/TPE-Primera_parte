package org.example.microserviciomonopatin.controller;


import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.microserviciomonopatin.service.MonopatinMapper;
import org.example.microserviciomonopatin.service.MonopatinService;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService monopatinService;
    @Autowired
    private MonopatinMapper monopatinMapper;

    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody MonopatinDTO monopatinDTO) {
        Monopatin savedMonopatin = monopatinService.save(monopatinDTO);
        return new ResponseEntity<>(savedMonopatin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id) {
       MonopatinDTO monopatin = monopatinService.findById(id);
        return new ResponseEntity<>(monopatin, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonopatinDTO> updateMonopatin(@PathVariable Long id, @RequestBody MonopatinDTO monopatinDTO) {
        MonopatinDTO updatedMonopatin = monopatinService.update(id, monopatinDTO);
        return new ResponseEntity<>(updatedMonopatin, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonopatin(@PathVariable Long id) {
        monopatinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/crearMonopatines")
    public ResponseEntity<List<MonopatinDTO>> crearMonopatines(@RequestBody List<MonopatinDTO> monopatinDTOs) {
        List<Monopatin> savedMonopatines = monopatinService.saveAll(monopatinDTOs);
        List<MonopatinDTO> savedMonopatinesDTOs = savedMonopatines.stream()
                .map(monopatinMapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(savedMonopatinesDTOs, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MonopatinDTO>> getAllMonopatines() {
        List<MonopatinDTO> monopatines = monopatinService.findAll();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    // Endpoint para verificar si el monopatín existe
    @GetMapping("/monopatin/{id}")
    public boolean existeMonopatin(@PathVariable("id") Long monopatinId) {
        return monopatinService.existeMonopatin(monopatinId);
    }

    @GetMapping("/reporteUso")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporte() {
        List<ReporteUsoDTO> reportesUso = monopatinService.generarReporteUso();
        return ResponseEntity.ok(reportesUso);
    }

    // Obtener reporte por kilómetros recorridos
    @GetMapping("/reporteUso/km")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteKm() {
        List<ReporteUsoDTO> reportes = monopatinService.generarReportePorKilometros();
        return ResponseEntity.ok(reportes);
    }

    // Punto a- Endpoint para obtener reporte de tiempos con un parámetro opcional para incluir o excluir tiempos con pausa
    @GetMapping("/reporteUso/filtro-tiempos")
    public ResponseEntity<List<ReporteUsoDTO>> obtenerReporteTiempos(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa) {

        boolean excludeSinPausa = !includeSinPausa;
        List<ReporteUsoDTO> reportes = monopatinService.generarReporteTiempos(excludeSinPausa);
        return ResponseEntity.ok(reportes);
    }

    // Punto e-Endpoint para obtener la cantidad de monopatines en operación y mantenimiento
    @GetMapping("/estado/reporte")
    public ResponseEntity<Map<String, Long>> obtenerReporteEstadoMonopatines() {
        Map<String, Long> reporte = monopatinService.obtenerCantidadMonopatinesPorEstado();
        return ResponseEntity.ok(reporte);
    }
}
