package org.example.microserviciousuario.service;


import org.example.microserviciousuario.entity.Cuenta;
import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.exception.CuentaAnuladaException;
import org.example.microserviciousuario.exception.CuentaNotFoundException;
import org.example.microserviciousuario.exception.SaldoInsuficienteException;
import org.example.microserviciousuario.exception.UsuarioNotFoundException;
import org.example.microserviciousuario.repository.CuentaRepository;
import org.example.microserviciousuario.repository.UsuarioRepository;
import org.example.microserviciousuario.service.mapper.CuentaMapper;
import org.example.shareddto.DTO.entity.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaMapper cuentaMapper;


    public Cuenta save(CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaMapper.mapToEntity(cuentaDTO);
        return cuentaRepository.save(cuenta);
    }


    public CuentaDTO findById(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return cuentaMapper.mapToDTO(cuenta);
    }

    public List<Cuenta> agregarCuentas(List<CuentaDTO> cuentasDTO) {
         List<Cuenta> cuentas = cuentaMapper.mapToEntityList(cuentasDTO);
        return cuentaRepository.saveAll(cuentas);
    }

    public List<CuentaDTO> findAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        return cuentaMapper.mapToDTOList(cuentas);
    }

    public Cuenta updateCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + id));

        cuenta.setAnulada(cuentaDTO.isAnulada());
        return cuentaRepository.save(cuenta);
    }

    public void delete(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setAnulada(true); // O puedes eliminar de forma lógica
        cuentaRepository.save(cuenta);
    }

    public boolean existeCuenta(Long cuentaId) {
        return cuentaRepository.existsById(cuentaId);
    }

    public void asociarUsuarioACuenta(Long cuentaId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new CuentaAnuladaException("No se puede asociar un usuario a una cuenta anulada");
        }

        if (!cuenta.getUsuarios().contains(usuario)) {
            cuenta.agregarUsuario(usuario);
            cuentaRepository.save(cuenta);
        }
    }


    public String descontarSaldo(Long cuentaId, double monto) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new IllegalArgumentException("La cuenta está anulada y no se puede realizar esta operación");
        }
        if (cuenta.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar la operación");
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        cuentaRepository.save(cuenta);
        return "Saldo descontado correctamente";
    }

    public void anularCuenta(Long cuentaId, CuentaDTO cuentaDTO) {
        // Primero, encontramos la cuenta por ID
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (cuenta.isAnulada()) {
            throw new RuntimeException("La cuenta ya está anulada");
        }

        cuenta.setAnulada(cuentaDTO.isAnulada());

        cuentaRepository.save(cuenta);
    }
}
