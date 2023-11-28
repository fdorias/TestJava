package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.RespuestaRepository;
import com.pruebaspring.prueba.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class RespuestaServicesImpl implements RespuestaService{

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Override
    public Respuesta respuesta(Usuario usuarioCreado) {
        Respuesta respuesta = new Respuesta();
        Respuesta saveRespuesta = new Respuesta();
        String token = null;
        if (usuarioCreado != null) {
            token = jwtUtil.generateToken(usuarioCreado);
        }
        String fecha = fechaCreacionUsuario();
        String modificado = fechaModificacionUsuario();
        String ultimoLogin = fechaUltimoLogin();
        String activo = validarActivo(token, usuarioCreado);

        respuesta.setId(usuarioCreado.getId());
        respuesta.setCreado(fecha);
        respuesta.setModificado(modificado);
        respuesta.setUltimoLogin(ultimoLogin);
        respuesta.setToken(token);
        respuesta.setActivo(activo);
        saveRespuesta = respuestaRepository.save(respuesta);
        return saveRespuesta;
    }

    private String validarActivo(String token, Usuario usuarioCreado) {
        boolean esValido = jwtUtil.isTokenValid(token,usuarioCreado );
        String respuesta = null;
        if (esValido = true){
            respuesta = "Activo";
        }else {
            respuesta = "No Activo";
        }
        return respuesta;
    }

    private String fechaCreacionUsuario(){

        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }

    private String fechaModificacionUsuario(){
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }
    private String fechaUltimoLogin(){
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }
}
