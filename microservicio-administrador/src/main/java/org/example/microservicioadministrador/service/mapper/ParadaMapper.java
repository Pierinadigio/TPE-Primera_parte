package org.example.microservicioadministrador.service.mapper;

import org.example.microservicioadministrador.entity.Parada;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.stereotype.Component;

@Component
public class ParadaMapper {

    public ParadaDTO mapToDTO(Parada parada) {
        ParadaDTO dto = new ParadaDTO();
        dto.setId(parada.getId());
        dto.setLatitud(parada.getLatitud());
        dto.setLongitud(parada.getLongitud());
        return dto;
    }

    public Parada mapToEntity(ParadaDTO paradaDTO) {
        Parada parada = new Parada();
        parada.setId(paradaDTO.getId());
        parada.setLatitud(paradaDTO.getLatitud());
        parada.setLongitud(paradaDTO.getLongitud());
        return parada;
    }
}
