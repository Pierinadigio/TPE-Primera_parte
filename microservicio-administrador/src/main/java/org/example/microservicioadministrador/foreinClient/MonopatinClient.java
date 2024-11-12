package org.example.microservicioadministrador.foreinClient;


import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009/monopatines")
public interface MonopatinClient {

    @GetMapping("/{id}")
    ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PostMapping
    MonopatinDTO guardarMonopatin(@RequestBody MonopatinDTO monopatin);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarMonopatin(@PathVariable Long id);


    //KM  recorridos de un determinado Monopatin
    @GetMapping("/{id}/totalKm")
    ResponseEntity<Double> getTotalKmRecorridos(@PathVariable Long id);

    @GetMapping("/{id}/tiempoUsoConPausa")
    ResponseEntity<Long> obtenerTiempoUsoConPausa(@PathVariable("id") Long id);

    @GetMapping("/{id}/tiempoUsoSinPausa")
    ResponseEntity<Long> obtenerTiempoUsoSinPausa(@PathVariable("id") Long id);

    @GetMapping("/estado/reporte")
    Map<String, Long> obtenerReporteEstadoMonopatines();
}
