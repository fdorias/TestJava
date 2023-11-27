package com.pruebaspring.prueba.controllers;

import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.UsuarioRepository;
import com.pruebaspring.prueba.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping(value = "/crearUsuario")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping(value = "/usuarios")
    public List<Usuario> usuarios(){
        return usuarioService.buscarUsuarios();
    }

    @GetMapping(value = "/eliminarUsuarios/{id}")
    public void eliminar(@PathVariable Long id){
        usuarioService.EliminarUsuario(id);
    }

}
