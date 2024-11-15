package org.example.microservicioviaje.controller;

import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.service.PausaService;
import org.example.shareddto.DTO.entity.PausaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pausas")
public class PausaController {

    @Autowired
    private PausaService pausaService;


    @PostMapping("/altaPausa/{viajeId}")
    public ResponseEntity<PausaDTO> crearPausa(
            @PathVariable Long viajeId,
            @RequestParam("horaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaInicio,
            @RequestParam("horaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaFin) {

        try {
            PausaDTO pausaDTO = new PausaDTO();
            pausaDTO.setHoraInicio(horaInicio);
            pausaDTO.setHoraFin(horaFin);
            pausaDTO.setViajeId(viajeId);
            PausaDTO nuevaPausaDTO = pausaService.altaPausa(pausaDTO.getViajeId(), pausaDTO.getHoraInicio(), pausaDTO.getHoraFin());

            return new ResponseEntity<>(nuevaPausaDTO, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Endpoint para obtener una pausa por su ID
    @GetMapping("/{pausaId}")
    public ResponseEntity<PausaDTO> obtenerPausa(@PathVariable Long pausaId) {
        PausaDTO pausa = pausaService.obtenerPausa(pausaId);
        return ResponseEntity.ok(pausa);
    }

    // Endpoint para actualizar una pausa
    @PutMapping("/{pausaId}")
    public ResponseEntity<PausaDTO> actualizarPausa(
            @PathVariable Long pausaId,
            @RequestBody PausaDTO pausaDTO) {

        PausaDTO updatedPausa = pausaService.actualizarPausa(
                pausaId,
                pausaDTO.getHoraInicio(),
                pausaDTO.getHoraFin()
        );
        return ResponseEntity.ok(updatedPausa);
    }

    // Endpoint para eliminar una pausa
    @DeleteMapping("/{pausaId}")
    public ResponseEntity<Void> eliminarPausa(@PathVariable Long pausaId) {
        pausaService.eliminarPausa(pausaId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener todas las pausas
    @GetMapping
    public List<PausaDTO> obtenerTodasLasPausas() {
        return pausaService.obtenerTodasLasPausas();
    }
}
