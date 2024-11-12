package org.example.microservicioadministrador.controller;


import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.service.ParadaService;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    private ParadaService paradaService;


    @PostMapping("/registarParada")
    public ResponseEntity<Parada> registrarParada(@RequestBody ParadaDTO paradaDTO) {
        Parada createdParada = paradaService.save(paradaDTO);
        return new ResponseEntity<>(createdParada, HttpStatus.CREATED);
    }

    @PostMapping("/agregarParadas")
    public ResponseEntity<List<Parada>> agregarParadas(@RequestBody List<ParadaDTO> paradaDTOList) {
        List<Parada> paradasGuardadas = paradaService.saveAll(paradaDTOList);
        return new ResponseEntity<>(paradasGuardadas, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaDTO> getParadaById(@PathVariable Long id) {
        ParadaDTO parada = paradaService.findById(id);
        return new ResponseEntity<>(parada, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParadaDTO> updateParada(@PathVariable Long id, @RequestBody ParadaDTO paradaDTO) {
        ParadaDTO updatedParada = paradaService.update(id, paradaDTO);
        return new ResponseEntity<>(updatedParada, HttpStatus.OK);
    }

    @DeleteMapping("/quitarParada/{id}")
    public ResponseEntity<Void> quitarParada(@PathVariable Long id) {
        paradaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ParadaDTO>> getAllParadas() {
        List<ParadaDTO> paradas = paradaService.findAll();
        return new ResponseEntity<>(paradas, HttpStatus.OK);
    }

}
