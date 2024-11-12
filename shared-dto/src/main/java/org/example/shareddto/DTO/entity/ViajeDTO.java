package org.example.shareddto.DTO.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViajeDTO {
    private Long id;
    private Long cuentaId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private long monopatinId;
    private boolean enPausa;
    private boolean fuePausado;
    private double costoTotal;
    private double totalKmRecorridos;
    private long totalTiempo;
    private long totalTiempoUsoSinPausas;


}
