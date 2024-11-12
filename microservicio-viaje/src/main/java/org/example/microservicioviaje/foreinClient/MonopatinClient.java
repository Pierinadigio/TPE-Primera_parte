package org.example.microservicioviaje.foreinClient;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009/monopatines")
public interface MonopatinClient {
    @GetMapping("/{id}")
   ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PostMapping
    Monopatin guardarMonopatin(@RequestBody MonopatinDTO monopatin);

    @PutMapping("/{id}")
    ResponseEntity<MonopatinDTO> updateMonopatin(@PathVariable Long id, @RequestBody MonopatinDTO monopatinDTO);

    @GetMapping("/monopatin/{id}")
    boolean existeMonopatin(@PathVariable("id") Long monopatinId);
}