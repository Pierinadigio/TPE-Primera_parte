package org.example.microservicioviaje.controller;


import jakarta.validation.Valid;
import org.example.microservicioviaje.entity.Viaje;
import org.example.microservicioviaje.service.ViajeService;
import org.example.shareddto.DTO.ReporteMonopatinDTO;
import org.example.shareddto.DTO.entity.ViajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;


    //Endpoint para dar de alta un viaje
    @PostMapping
    public ResponseEntity<ViajeDTO> crearViaje(@Valid @RequestBody ViajeDTO viajeDTO) {
        ViajeDTO viajeCreado = viajeService.guardarViaje(viajeDTO);
        return new ResponseEntity<>(viajeCreado, HttpStatus.CREATED);
    }

    // Endpoint para iniciar un viaje con Monopatin y cuenta de usuario
    @PostMapping("/iniciar/monopatin/{monopatinId}/cuenta/{cuentaId}")
    public ResponseEntity<Viaje> iniciarViaje(@PathVariable Long monopatinId, @PathVariable Long cuentaId) {
        Viaje createdViaje = viajeService.iniciarViaje(monopatinId, cuentaId);
        return new ResponseEntity<>(createdViaje, HttpStatus.CREATED);
    }

   //Endpoint para obtener viaje por id
    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> obtenerViajePorId(@PathVariable Long id) {
        ViajeDTO viaje = viajeService.obtenerViajePorId(id);
        return ResponseEntity.ok(viaje);
    }

    // Endpoint para actualizar un viaje
    @PutMapping("/{id}")
    public ResponseEntity<ViajeDTO> updateViaje(@PathVariable Long id, @Valid @RequestBody ViajeDTO viajeDTO) {
        System.out.println("Recibiendo datos: " + viajeDTO);  // Agrega esto para ver qué datos estás recibiendo
        try {
            ViajeDTO updatedViaje = viajeService.updateViaje(id, viajeDTO);
            return ResponseEntity.ok(updatedViaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // Endpoint para eliminar un viaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        viajeService.deleteViaje(id);
        return ResponseEntity.noContent().build();
    }


    //Endpoint para varios viajes
    @PostMapping("/altaViajes")
    public ResponseEntity<List<ViajeDTO>> altaViajes(@Valid@RequestBody List<ViajeDTO> viajeDTOs) {
        List<ViajeDTO> viajesGuardados = viajeService.altaViajes(viajeDTOs);
        return ResponseEntity.ok(viajesGuardados);
    }

    // Endpoint para obtener todos los viajes
    @GetMapping
    public ResponseEntity<List<ViajeDTO>> obtenerViajes() {
        List<ViajeDTO> viajes = viajeService.obtenerViajes();
        return ResponseEntity.ok(viajes);
    }

    // Endpoint para pausar un viaje
    @PostMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarViaje(@PathVariable Long id) {
        viajeService.pausarViaje(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint para reanudar un viaje
    @PostMapping("/{id}/reanudar")
    public ResponseEntity<Void> reanudarViaje(@PathVariable Long id) {
        viajeService.reanudarViaje(id);
        return ResponseEntity.ok().build();
    }


    // Endpoint para ubicar un monopatín en una parada
    @PostMapping("/finalizar/{viajeId}/monopatin/{monopatinId}/parada/{paradaId}")
    public ResponseEntity<Void> ubicarMonopatinEnParada(
            @PathVariable Long viajeId,
            @PathVariable Long monopatinId,
            @PathVariable Long paradaId) {
        viajeService.finalizarViaje(viajeId, monopatinId, paradaId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para calcular el costo del viaje
    @GetMapping("/{id}/costo")
    public ResponseEntity<Double> calcularCostoDelViaje(@PathVariable Long id) {
        double costo = viajeService.calcularCostoDelViaje(id);
        return ResponseEntity.ok(costo);
    }

    @GetMapping("/{id}/tiempo-uso")
    public ResponseEntity<Long> calcularTiempoUso(@PathVariable Long id) {
        try {
           long tiempoUso = viajeService.calcularTiempoUso(id);
            return ResponseEntity.ok(tiempoUso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 si hay un problema con las fechas
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 si el viaje no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 500 para otros errores
        }
    }

    // Endpoint para obtener el reporte de monopatines con más de X viajes en un año
    @GetMapping("/reporte/monopatines")
    public ResponseEntity<List<ReporteMonopatinDTO>> obtenerMonopatinesConMasDeXViajes(
            @RequestParam("anio") int anio,
            @RequestParam("minViajes") long minViajes) {

        List<ReporteMonopatinDTO> monopatines = viajeService.obtenerMonopatinesConMasDeXViajes(anio, minViajes);

        return ResponseEntity.ok(monopatines);
    }

    // Endpoint para obtener el total facturado en un determinado anio en un rango de meses
    @GetMapping("/facturado")
    public ResponseEntity<Double> obtenerTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {
        Double totalFacturado = viajeService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(totalFacturado);
    }

    //Endpoint para obtener los viajes realizados por un monopatin
    @GetMapping("/viajesPor/{monopatinId}")
    public ResponseEntity<List<ViajeDTO>> obtenerViajesPorMonopatinId(@PathVariable long monopatinId) {
        List<ViajeDTO> viajes = viajeService.obtenerViajesPorMonopatinId(monopatinId);
        return ResponseEntity.ok(viajes);
    }



}
