package org.example.shareddto.DTO.entity;

import lombok.Data;

@Data
public class MonopatinDTO {
    private Long id;
    private String modelo;
    private double latitud;
    private double longitud;
    private String estado;
    private boolean disponible;
    private double totalKmRecorridos;
    private Long tiempoUsoConPausa;
    private Long tiempoUsoSinPausa;

    public MonopatinDTO() {}

    public MonopatinDTO(Long id, String modelo, double latitud, double longitud, String estado, boolean disponible, double totalKmRecorridos, Long tiempoUsoConPausa, Long tiempoUsoSinPausa) {
        this.id = id;
        this.modelo = modelo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.disponible = disponible;
        this.totalKmRecorridos = totalKmRecorridos;
        this.tiempoUsoConPausa = tiempoUsoConPausa;
        this.tiempoUsoSinPausa = tiempoUsoSinPausa;
    }

    public void validate() {
        if (modelo == null || modelo.isEmpty()) {
            throw new IllegalArgumentException("El modelo del monopatín no puede estar vacío");
        }
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }
}


