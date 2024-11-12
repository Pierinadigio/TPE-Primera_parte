package org.example.microserviciousuario.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    @Column(nullable = false)
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private double latitud;
    private double longitud;

    @ManyToMany(mappedBy = "usuarios")
    private List<Cuenta> cuentasApp = new ArrayList<>();


    public void agregarCuenta(Cuenta cuenta) {
        if (!this.cuentasApp.contains(cuenta)) {
            this.cuentasApp.add(cuenta);
            cuenta.getUsuarios().add(this);
        }
    }

}