package org.example.microservicioviaje.service;

import org.example.microservicioviaje.entity.Pausa;
import org.example.microservicioviaje.entity.Viaje;
import org.example.microservicioviaje.repository.PausaRepository;
import org.example.microservicioviaje.repository.ViajeRepository;
import org.example.microservicioviaje.service.Mapper.PausaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PausaService {

    @Autowired
    private PausaRepository pausaRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaMapper pausaMapper;


    public Pausa altaPausa(Long viajeId, LocalDateTime horaInicio, LocalDateTime horaFin) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("El viaje no existe."));

        Pausa pausa = new Pausa();
        pausa.setViaje(viaje);
        pausa.setHoraInicio(horaInicio);
        pausa.setHoraFin(horaFin);
        pausa.calcularDuracion();
        pausaRepository.save(pausa);

        viaje.getPausas().add(pausa);
        viajeRepository.save(viaje);
        return pausa;
    }


}