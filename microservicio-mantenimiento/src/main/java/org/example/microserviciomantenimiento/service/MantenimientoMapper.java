package org.example.microserviciomantenimiento.service;

import org.example.microserviciomantenimiento.entity.Mantenimiento;
import org.example.shareddto.DTO.entity.MantenimientoDTO;
import org.springframework.stereotype.Component;

@Component
public class MantenimientoMapper {
    public MantenimientoDTO toDTO(Mantenimiento mantenimiento) {
        if (mantenimiento == null) {
            return null;
        }

        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getMonopatinId(),
                mantenimiento.getFechaInicioMantenimiento(),
                mantenimiento.getFechaFinMantenimiento(),
                mantenimiento.getDescripcion()

        );
    }


    public Mantenimiento toEntity(MantenimientoDTO mantenimientoDTO) {
        if (mantenimientoDTO == null) {
            return null;
        }

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(mantenimientoDTO.getId());
        mantenimiento.setMonopatinId(mantenimientoDTO.getMonopatinId());
        mantenimiento.setFechaInicioMantenimiento(mantenimientoDTO.getFechaInicioMantenimiento());
        mantenimiento.setFechaFinMantenimiento(mantenimientoDTO.getFechaFinMantenimiento());
        mantenimiento.setDescripcion(mantenimientoDTO.getDescripcion());

        return mantenimiento;
    }
}
