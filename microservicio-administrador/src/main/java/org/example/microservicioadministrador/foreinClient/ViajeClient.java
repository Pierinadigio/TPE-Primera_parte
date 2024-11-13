package org.example.microservicioadministrador.foreinClient;

import org.example.shareddto.DTO.ReporteMonopatinDTO;
import org.example.shareddto.DTO.entity.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-viaje", url = "http://localhost:8010")
public interface  ViajeClient {

    @GetMapping("/viajes/{id}")
    ResponseEntity<ViajeDTO> obtenerViajePorId(@PathVariable Long id);

    @PostMapping("/viajes")
    ResponseEntity<ViajeDTO> altaViaje(@RequestBody ViajeDTO viajeDTO);

    @PutMapping("/viajes/{id}")
    ResponseEntity<ViajeDTO> updateViaje(@PathVariable Long id, @RequestBody ViajeDTO viajeActualizado);

    @GetMapping("/viajes/reporte/monopatines")
    ResponseEntity<List<ReporteMonopatinDTO>> obtenerMonopatinesConMasDeXViajes(
            @RequestParam("anio") int anio,
            @RequestParam("minViajes") long minViajes);

    @GetMapping("/viajes/facturado")
    ResponseEntity<Double> obtenerTotalFacturado(
            @RequestParam("anio") int anio,
            @RequestParam("mesInicio") int mesInicio,
            @RequestParam("mesFin") int mesFin);

    @GetMapping("/viajes/{id}/costo")
    ResponseEntity<Double> calcularCostoDelViaje(@PathVariable Long id);

    @GetMapping("/viajes/{id}/tiempo-uso")
   ResponseEntity<Long> calcularTiempoUso(@PathVariable Long id);
}
