package org.example.microservicioadministrador.foreinClient;

import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8006/cuentas")
public interface CuentaClient {

    @GetMapping("/{id}")
    ResponseEntity<CuentaDTO> getCuenta(@PathVariable Long id);

    @PutMapping("/{id}/descontar")
    ResponseEntity<String> descontarSaldo(@PathVariable Long id, @RequestParam double monto);

    @PutMapping("/{cuentaId}/anular")
    ResponseEntity<Void> anularCuenta(@PathVariable Long cuentaId, @RequestBody CuentaDTO cuenta);
}