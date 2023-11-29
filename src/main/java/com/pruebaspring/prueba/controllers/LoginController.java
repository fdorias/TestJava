package com.pruebaspring.prueba.controllers;

import com.pruebaspring.prueba.model.Login;
import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.LoginRepository;
import com.pruebaspring.prueba.services.LoginService;
import com.pruebaspring.prueba.services.RespuestaService;
import com.pruebaspring.prueba.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;



    @PostMapping(value = "/login")
    public Respuesta login(@RequestBody Login login) {
        Respuesta sesion = loginService.validarUsuario(login);
        return sesion;
    }

    @PostMapping(value = "/logout")
    public Respuesta logout(@RequestBody Login login) {
        Respuesta sesion = loginService.logout(login);
        return sesion;
    }
}
