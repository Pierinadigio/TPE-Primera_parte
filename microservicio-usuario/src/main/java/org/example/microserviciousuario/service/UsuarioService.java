package org.example.microserviciousuario.service;


import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.exception.EmailAlreadyExistsException;
import org.example.microserviciousuario.exception.InvalidEmailException;
import org.example.microserviciousuario.exception.UsuarioNotFoundException;
import org.example.microserviciousuario.foreinClient.MonopatinClient;
import org.example.microserviciousuario.repository.UsuarioRepository;

import org.example.microserviciousuario.service.mapper.UsuarioMapper;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteMonopatinesCercanosDTO;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MonopatinClient monopatinClient;
    @Autowired
    private UsuarioMapper usuarioMapper;


    public List<Usuario> agregarUsuarios(List<UsuarioDTO> usuariosDTO) {
        usuariosDTO.forEach(usuarioDTO -> {
            validarEmail(usuarioDTO.getEmail());
            if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía");
            }
        });
        List<Usuario> usuarios = usuariosDTO.stream()
                .map(usuarioMapper::mapToEntity)
                .collect(Collectors.toList());
        return usuarioRepository.saveAll(usuarios);
    }

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public Usuario save(UsuarioDTO usuarioDTO) {
        validarEmail(usuarioDTO.getEmail());
        if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        Usuario usuario = usuarioMapper.mapToEntity(usuarioDTO);
        return usuarioRepository.save(usuario);
    }

    public UsuarioDTO findById(Long cuentaId) {
        Usuario usuario = usuarioRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.mapToDTO(usuario);
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setEmail(usuarioDTO.getEmail());
            Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
            return usuarioMapper.mapToDTO(usuarioActualizado);

    }

    // Eliminar un usuario
    public void delete(Long cuentaId) {
        Usuario usuario = usuarioRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.save(usuario);
    }


    public List<ReporteMonopatinesCercanosDTO> obtenerMonopatinesCercanos(Long usuarioId, double radio) {
        Usuario usuario = obtenerUsuarioPorId(usuarioId);

        double latitudUsuario = usuario.getLatitud();
        double longitudUsuario = usuario.getLongitud();

        ResponseEntity<List<MonopatinDTO>> response = monopatinClient.getAllMonopatines();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<MonopatinDTO> todosLosMonopatines = response.getBody();
            List<ReporteMonopatinesCercanosDTO> monopatinesCercanos = new ArrayList<>();

            for (MonopatinDTO monopatin : todosLosMonopatines) {
                double distancia = calcularDistancia(latitudUsuario, longitudUsuario, monopatin.getLatitud(), monopatin.getLongitud());
                if (distancia <= radio) {
                    monopatinesCercanos.add(new ReporteMonopatinesCercanosDTO(
                            monopatin.getId(),
                            monopatin.getModelo(),
                            monopatin.getLatitud(),
                            monopatin.getLongitud()
                    ));
                }
            }
            return monopatinesCercanos;
        } else {
            throw new RuntimeException("Error al obtener la lista de monopatines.");
        }
    }

    private void validarEmail(String email) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("El correo electrónico no es válido.");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Ya existe un usuario con el mismo email.");
        }
    }


    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    private Usuario obtenerUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Devuelve la distancia en km
    }




}