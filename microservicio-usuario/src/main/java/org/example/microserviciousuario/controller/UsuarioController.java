package org.example.microserviciousuario.controller;


import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.service.UsuarioService;
import org.example.shareddto.DTO.ReporteMonopatinesCercanosDTO;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crearUsuarios")
    public ResponseEntity<List<Usuario>> crearUsuarios(@RequestBody List<UsuarioDTO> usuariosDTO) {
        List<Usuario> usuariosCreado = usuarioService.agregarUsuarios(usuariosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosCreado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.findAll();
        return ResponseEntity.ok(usuariosDTO); // Devuelve la lista de usuarios
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = usuarioService.save(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        UsuarioDTO suarioDTO = usuarioService.findById(id);
        return new ResponseEntity<>(suarioDTO, HttpStatus.OK);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.update(id, usuarioDTO);
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   //Punto g -Endpoint para listar monopatines cercanos
    @GetMapping("/{usuarioId}/monopatines-cercanos")
    public ResponseEntity<List<ReporteMonopatinesCercanosDTO>> obtenerMonopatinesCercanos(
            @PathVariable Long usuarioId,
            @RequestParam(defaultValue = "5") double radio) { // Radio por defecto de 5 km

        List<ReporteMonopatinesCercanosDTO> monopatinesCercanos = usuarioService.obtenerMonopatinesCercanos(usuarioId, radio);

        return ResponseEntity.ok(monopatinesCercanos);
    }


}
