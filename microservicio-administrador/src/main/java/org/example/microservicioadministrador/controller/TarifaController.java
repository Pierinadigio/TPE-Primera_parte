package org.example.microservicioadministrador.controller;

import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.service.TarifaService;
import org.example.shareddto.DTO.entity.TarifaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;


    @PostMapping("/crearTarifas")
    public ResponseEntity<List<Tarifa>> guardarTarifas(@RequestBody List<TarifaDTO> tarifasDTO) {
        List<Tarifa> tarifasGuardadas = tarifaService.saveAll(tarifasDTO);
        return new ResponseEntity<>(tarifasGuardadas, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Tarifa> crearTarifa(@RequestBody TarifaDTO tarifaDTO) {
        Tarifa createdTarifa = tarifaService.save(tarifaDTO);
        return new ResponseEntity<>(createdTarifa, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaDTO> obtenerTarifa(@PathVariable Long id) {
        TarifaDTO tarifaDTO = tarifaService.findById(id);
        return ResponseEntity.ok(tarifaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaDTO> actualizarTarifa(@PathVariable Long id, @RequestBody TarifaDTO tarifaDTO) {
        TarifaDTO updatedTarifa = tarifaService.update(id, tarifaDTO);
        return new ResponseEntity<>(updatedTarifa, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable Long id) {
        tarifaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tarifa/{id}")
    public boolean existeTarifa(@PathVariable("id") Long tarifaId) {
        return tarifaService.existeTarifa(tarifaId);
    }

    @GetMapping
    public ResponseEntity<List<TarifaDTO>> getAllTarifas() {
        List<TarifaDTO> tarifas = tarifaService.findAll();
        return new ResponseEntity<>(tarifas, HttpStatus.OK);
    }

    @GetMapping("/actual")
    public ResponseEntity<TarifaDTO> obtenerUltimaTarifa() {
        TarifaDTO tarifaActual = tarifaService.obtenerTarifaActual();
        return new ResponseEntity<>(tarifaActual, HttpStatus.OK);
    }




}
