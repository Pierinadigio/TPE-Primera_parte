package org.example.microservicioviaje.foreinClient;

import org.example.microserviciousuario.entity.Cuenta;
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

    @PutMapping("/{id}")
    ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO);

    @GetMapping("/cuenta/{id}")
    boolean existeCuenta(@PathVariable("id") Long cuentaId);
}
