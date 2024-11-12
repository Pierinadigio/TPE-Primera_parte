package org.example.microservicioadministrador.service;

import org.example.microservicioadministrador.entity.Parada;
import org.example.microservicioadministrador.exception.ResourceNotFoundException;
import org.example.microservicioadministrador.repository.ParadaRepository;
import org.example.microservicioadministrador.service.mapper.ParadaMapper;
import org.example.shareddto.DTO.entity.ParadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository paradaRepository;

    @Autowired
    private ParadaMapper paradaMapper;


    public Parada save(ParadaDTO paradaDTO) {
        paradaDTO.validate();
        Parada parada = paradaMapper.mapToEntity(paradaDTO);
        return paradaRepository.save(parada);
    }

    public List<Parada> saveAll(List<ParadaDTO> paradaDTOList) {
        paradaDTOList.forEach(ParadaDTO::validate);

        List<Parada> paradaList = paradaDTOList.stream()
                .map(paradaMapper::mapToEntity)
                .collect(Collectors.toList());
        return paradaRepository.saveAll(paradaList);
    }

    public ParadaDTO findById(Long id) {
        Parada parada = getParadaById(id);  // Reutiliza el metodo
        return paradaMapper.mapToDTO(parada);
    }

    public List<ParadaDTO> findAll() {
        return paradaRepository.findAll().stream()
                .map(paradaMapper::mapToDTO)
                .collect(Collectors.toList());
    }


    public ParadaDTO update(Long id, ParadaDTO paradaDTO) {
        Parada parada = getParadaById(id);
        paradaDTO.validate();

        parada.setLatitud(paradaDTO.getLatitud());
        parada.setLongitud(paradaDTO.getLongitud());
        return paradaMapper.mapToDTO(paradaRepository.save(parada));
    }


    public void delete(Long id) {
        Parada parada = getParadaById(id);
        paradaRepository.delete(parada);
    }


    private Parada getParadaById(Long id) {
        return paradaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parada no encontrada"));
    }



}

