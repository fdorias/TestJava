package com.pruebaspring.prueba.repository;

import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

}
