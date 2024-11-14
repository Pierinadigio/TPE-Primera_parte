package org.example.shareddto.DTO;

import lombok.Data;

@Data
public class ReporteMonopatinDTO {
    private Long monopatinId;
  ////  private String modelo;
 //   private String estado;
    private long cantidadViajes;


    public ReporteMonopatinDTO() {
    }

    public ReporteMonopatinDTO(Long monopatinId, long cantidadViajes) {
        this.monopatinId = monopatinId;
        this.cantidadViajes = cantidadViajes;
    }
/*
    public ReporteMonopatinDTO(Long monopatinId, String modelo, String estado, long cantidadViajes) {
        this.monopatinId = monopatinId;
        this.modelo = modelo;
        this.estado = estado;
        this.cantidadViajes = cantidadViajes;
    }
*/

}
