package org.example.shareddto.DTO.entity;

import lombok.Data;

@Data
public class ParadaDTO {
    private Long id;
    private double latitud;
    private double longitud;

    public ParadaDTO() {
    }
    public ParadaDTO(Long id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void validate() {

        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
    }


}
