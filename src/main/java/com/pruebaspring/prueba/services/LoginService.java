package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.model.Login;
import com.pruebaspring.prueba.model.Respuesta;

public interface LoginService {
    Respuesta validarUsuario(Login login);

    Respuesta logout(Login login);
}
