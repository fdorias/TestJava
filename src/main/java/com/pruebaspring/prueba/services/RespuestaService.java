package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;

import java.util.Map;

public interface RespuestaService {
    Respuesta respuesta(Usuario usuarioCreado);

    Respuesta actualizarUsuario(Usuario usuario,Long id);

    Respuesta actualizarContraseña(Long id, Map<String, String> request);
}
