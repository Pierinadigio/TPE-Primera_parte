package org.example.microservicioviaje.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cuenta_id", nullable = false)
    private Long cuentaId;
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private long monopatinId;
    private boolean enPausa;
    private boolean fuePausado;
    private double costoTotal;
    private final long pausaMAX = 15;
    private double totalKmRecorridos;
    private long totalTiempo;
    private long totalTiempoUsoSinPausas;
    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pausa> pausas;






    @Override
    public String toString() {
        return "Viaje{" +
                ", id=" + id +
                "monopatin=" + monopatinId +

                '}';
    }
}

