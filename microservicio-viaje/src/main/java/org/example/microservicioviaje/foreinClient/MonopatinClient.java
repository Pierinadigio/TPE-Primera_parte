package org.example.microservicioviaje.foreinClient;

import org.example.microserviciomonopatin.entity.Monopatin;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8009")
public interface MonopatinClient {

   @GetMapping("/monopatines/{id}")
   ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PostMapping("/monopatines")
    Monopatin guardarMonopatin(@RequestBody MonopatinDTO monopatin);

    @PutMapping("/monopatines/{id}")
    ResponseEntity<MonopatinDTO> updateMonopatin(@PathVariable Long id, @RequestBody MonopatinDTO monopatinDTO);

    @GetMapping("/monopatines/monopatin/{id}")
    boolean existeMonopatin(@PathVariable("id") Long monopatinId);
}
