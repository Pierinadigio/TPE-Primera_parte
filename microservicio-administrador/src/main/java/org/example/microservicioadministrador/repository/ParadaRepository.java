package org.example.microservicioadministrador.repository;

import org.example.microservicioadministrador.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}
