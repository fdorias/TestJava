package com.pruebaspring.prueba.controllers;

import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.model.Login;
import com.pruebaspring.prueba.services.RespuestaService;
import com.pruebaspring.prueba.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private RespuestaService respuestaService;



    @PostMapping(value = "/crearUsuario")
    public Respuesta crearUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.save(usuario);
        Respuesta respuesta = respuestaService.respuesta(usuarioCreado);
        return respuesta;
    }

    @GetMapping(value = "/usuarios")
    public List<Usuario> usuarios(){
        return usuarioService.buscarUsuarios();
    }

    @RequestMapping(value = "/eliminarUsuarios/{id}")
    public void eliminar(@PathVariable Long id){
        usuarioService.EliminarUsuario(id);
    }

    @PutMapping(value = "/actualizarUsuario/{id}")
    public Respuesta actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Respuesta respuesta = respuestaService.actualizarUsuario(usuario,id);
        return respuesta;
    }


}
