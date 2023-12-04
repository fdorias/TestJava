package com.pruebaspring.prueba.model;

import org.junit.jupiter.api.Test;

public class modelTest {
    Login login = new Login();
    Respuesta respuesta = new Respuesta();
    Telefono telefono = new Telefono();
    Usuario usuario = new Usuario();

    @Test
    public void modelTest(){
        login.getPassword();
        login.getPassword();
        login.setEmail("test@email.com");
        login.setPassword("1");
        respuesta.getId();
        respuesta.getCreado();
        respuesta.getModificado();
        respuesta.getUltimoLogin();
        respuesta.getToken();
        respuesta.getActivo();
        respuesta.getSesion();
        respuesta.setId(1l);
        respuesta.setCreado("1");
        respuesta.setModificado("1");
        respuesta.setUltimoLogin("1");
        respuesta.setToken("1");
        respuesta.setActivo("1");
        respuesta.setSesion(true);
        telefono.getId();
        telefono.getNumero();
        telefono.getCodigoCiudad();
        telefono.getCodigoPais();
        telefono.setId(1l);
        telefono.setNumero("1");
        telefono.setCodigoCiudad("1");
        telefono.setCodigoPais("1");
        usuario.getId();
        usuario.getNombre();
        usuario.getEmail();
        usuario.getContraseña();
        usuario.setId(1l);
        usuario.setNombre("test");
        usuario.setEmail("test@email.com");
        usuario.setContraseña("1");

    }
}
