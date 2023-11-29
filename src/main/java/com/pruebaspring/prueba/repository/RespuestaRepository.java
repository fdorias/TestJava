package com.pruebaspring.prueba.repository;

import com.pruebaspring.prueba.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long>  {
    Optional<Respuesta> findById(Long id);

}
