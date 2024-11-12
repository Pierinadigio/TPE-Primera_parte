package org.example.microserviciomantenimiento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long monopatinId;
    private LocalDateTime fechaInicioMantenimiento;
    private LocalDateTime fechaFinMantenimiento;
    private String descripcion;



}



