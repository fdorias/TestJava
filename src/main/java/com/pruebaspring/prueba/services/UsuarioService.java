package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.model.Usuario;

import java.util.List;


public interface UsuarioService {
    List<Usuario> buscarUsuarios();

    void EliminarUsuario(Long id);
}
