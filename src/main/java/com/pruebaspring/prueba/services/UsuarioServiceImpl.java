package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public List<Usuario> buscarUsuarios() {
        List<Usuario> listaUsuario = new ArrayList<>();
        listaUsuario = usuarioRepository.findAll();
        return listaUsuario;
    }

    @Override
    public void EliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
