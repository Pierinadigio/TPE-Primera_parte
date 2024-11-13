package org.example.microservicioadministrador.controller;

import org.example.microservicioadministrador.service.AdministradorService;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteFacturadoDTO;
import org.example.shareddto.DTO.ReporteMonopatinesXviajesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;


    // Punto b- Endpoint para anular una cuenta
    @PutMapping("/{cuentaId}/anular")
    public ResponseEntity<Void> anularCuenta(@PathVariable Long cuentaId) {
        administradorService.anularCuenta(cuentaId);
        return ResponseEntity.noContent().build();  // Devuelve un HTTP 204 sin contenido
    }

    // Punto c -Endpoint para consultar el reporte de monopatines con más de X viajes
    @GetMapping("/reporte/monopatines")
    public ResponseEntity<ReporteMonopatinesXviajesDTO> obtenerReporteMonopatines(
            @RequestParam(value = "anio") int anio,
            @RequestParam(value = "minViajes") long minViajes) {

        ReporteMonopatinesXviajesDTO reporte = administradorService.obtenerReporteMonopatines(anio, minViajes);

        return ResponseEntity.ok(reporte);
    }

    //Punto d- Endpoint para consultar el totall facturado en un rango de meses
    @GetMapping("/reporte/facturado")
    public ResponseEntity<ReporteFacturadoDTO> generarReporteFacturado(
            @RequestParam("anio") int anio,
            @RequestParam("mesInicio") int mesInicio,
            @RequestParam("mesFin") int mesFin) {

        ReporteFacturadoDTO reporte = administradorService.generarReporteFacturado(anio, mesInicio, mesFin);

        return ResponseEntity.ok(reporte);
    }

    //Puento e- Endpoint para consultar la cantidad de monopatines en operacion vs en mantenimiento
    @GetMapping("/estado-monopatines")
    public ResponseEntity<Map<String, Long>> obtenerEstadoMonopatines() {
        administradorService.consultarEstadoMonopatines();
        return ResponseEntity.ok().build();
    }

    //Punto f- Endpoint para realizar ajuste de precios a partir de cierta fecha
    @PostMapping("/ajustes/tarifas")
    public ResponseEntity<String> ajustarTarifas(@RequestParam LocalDate fechaAjuste,
                                                 @RequestParam double porcentajeBase,
                                                 @RequestParam double porcentajeExtra) {
        try {
            administradorService.ajustarTarifas(fechaAjuste, porcentajeBase, porcentajeExtra);
            return ResponseEntity.ok("Ajuste de tarifas realizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al ajustar tarifas: " + e.getMessage());
        }
    }


    // Endpoint para agregar un monopatín
    @PostMapping("/monopatines")
    public ResponseEntity<MonopatinDTO> agregarMonopatin(@RequestBody MonopatinDTO monopatin) {
        MonopatinDTO nuevoMonopatin = administradorService.agregarMonopatin(monopatin);
        return ResponseEntity.ok(nuevoMonopatin);
    }

    // Endpoint para eliminar un monopatín
    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<Void> quitarMonopatin(@PathVariable Long id) {
        administradorService.quitarMonopatin(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener un monopatín por ID
    @GetMapping("/monopatines/{id}")
    public ResponseEntity<MonopatinDTO> obtenerMonopatin(@PathVariable Long id) {
        MonopatinDTO monopatin = administradorService.obtenerMonopatin(id);
        return ResponseEntity.ok(monopatin);
    }
}
