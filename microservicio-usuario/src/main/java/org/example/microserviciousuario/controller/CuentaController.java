package org.example.microserviciousuario.controller;


import org.example.microserviciousuario.entity.Cuenta;
import org.example.microserviciousuario.exception.CuentaNotFoundException;
import org.example.microserviciousuario.service.CuentaService;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;


    @PostMapping("/crearCuentas")
    public ResponseEntity<List<Cuenta>> crearCuentas(@RequestBody List<CuentaDTO> cuentasDTO) {
        List<Cuenta> cuentasCreadas = cuentaService.agregarCuentas(cuentasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentasCreadas);
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody CuentaDTO cuentaDTO) {
        Cuenta savedCuenta = cuentaService.save(cuentaDTO);
        return new ResponseEntity<>(savedCuenta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id) {
        CuentaDTO cuenta = cuentaService.findById(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAllCuentas() {
        List<CuentaDTO> cuentas = cuentaService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuentaDTO);
        return ResponseEntity.ok(updatedCuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cuenta/{id}")
    public boolean existeCuenta(@PathVariable("id") Long cuentaId) {
        return cuentaService.existeCuenta(cuentaId);
    }

    @PostMapping("/{cuentaId}/usuarios/{usuarioId}")
    public ResponseEntity<Void> agregarUsuarioACuenta(@PathVariable Long cuentaId, @PathVariable Long usuarioId) {
        cuentaService.asociarUsuarioACuenta(cuentaId, usuarioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/descontar")
    public ResponseEntity<String> descontarSaldo(@PathVariable Long id, @RequestParam double monto) {
        try {
            String response = cuentaService.descontarSaldo(id, monto);
            return ResponseEntity.ok(response);
        } catch (CuentaNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{cuentaId}/anular")
    public ResponseEntity<Void> anularCuenta(@PathVariable Long cuentaId, @RequestBody CuentaDTO cuentaDTO) {
        // Delegamos la tarea de anulación al servicio
        cuentaService.anularCuenta(cuentaId, cuentaDTO);

        // Retornamos una respuesta sin contenido (HTTP 204)
        return ResponseEntity.noContent().build();
    }

}