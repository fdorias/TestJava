package com.pruebaspring.prueba.repository;

import com.pruebaspring.prueba.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario save(Usuario usuario);
}
