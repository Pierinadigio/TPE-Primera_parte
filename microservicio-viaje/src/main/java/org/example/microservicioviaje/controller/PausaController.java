package org.example.microservicioviaje.controller;

import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.service.PausaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pausas")
public class PausaController {

    @Autowired
    private PausaService pausaService;


    @PostMapping("/alta/{viajeId}")
    public ResponseEntity<Pausa> crearPausa(
            @PathVariable Long viajeId,
            @RequestParam("horaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaInicio,
            @RequestParam("horaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaFin) {

        try {
            Pausa nuevaPausa = pausaService.altaPausa(viajeId, horaInicio, horaFin);
            return new ResponseEntity<>(nuevaPausa, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // 400 si hay un problema con los parámetros.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 si ocurre un error inesperado.
        }
    }

}
