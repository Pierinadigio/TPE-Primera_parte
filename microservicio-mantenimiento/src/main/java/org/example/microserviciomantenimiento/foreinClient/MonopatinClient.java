package org.example.microserviciomantenimiento.foreinClient;

import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.shareddto.DTO.ReporteUsoDTO;
import java.util.List;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009/monopatines")
public interface MonopatinClient {


    @GetMapping("/{id}")
    ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ResponseEntity<MonopatinDTO> updateMonopatin(@PathVariable("id") Long id, @RequestBody MonopatinDTO monopatinDTO);

    @PostMapping
    ResponseEntity<MonopatinDTO> createMonopatin(@RequestBody MonopatinDTO monopatinDTO);

    @GetMapping("/reporteUso/filtro-tiempos")
    ResponseEntity<List<ReporteUsoDTO>> obtenerReporteUsoConTiempos(
            @RequestParam(value = "includeSinPausa", required = false, defaultValue = "true") Boolean includeSinPausa);





}