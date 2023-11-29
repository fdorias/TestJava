package com.pruebaspring.prueba.repository;

import com.pruebaspring.prueba.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginRepository extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
