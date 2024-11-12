package org.example.microserviciomonopatin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monopatin_id", nullable = false, unique = true)
    private Long id;
    private String modelo;
    private double latitud;
    private double longitud;
    private String estado; // "en_operacion", "en_mantenimiento"
    private boolean disponible;
    private Long tiempoUsoConPausa;
    private Long tiempoUsoSinPausa;
    private double totalKmRecorridos;



    @Override
    public String toString() {
        return "Monopatin{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                '}';
    }
}
