package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.exception.UsuarioException;
import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.RespuestaRepository;
import com.pruebaspring.prueba.repository.UsuarioRepository;
import com.pruebaspring.prueba.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class RespuestaServicesImpl implements RespuestaService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Respuesta respuesta(Usuario usuarioCreado) {
        Respuesta respuesta = new Respuesta();
        Respuesta saveRespuesta ;
        String token ;
        try {
            if (usuarioCreado != null) {
                token = jwtUtil.generateToken(usuarioCreado);
            }else {
                throw new UsuarioException("En generar el token");
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
            respuesta.setSesion(false);
            saveRespuesta = respuestaRepository.save(respuesta);
            return saveRespuesta;

        }catch (UsuarioException e) {
            LOGGER.error("Error en la validación del usuario", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Mensaje de error", e.getMessage(), e);
            throw new UsuarioException("Error inesperado al procesar el usuario");
        }

    }

    @Override
    public Respuesta actualizarUsuario(Usuario usuario,Long id) {
        Optional<Respuesta> respuesta = Optional.of(new Respuesta());
        Respuesta saveRespuesta = new Respuesta();
        Optional<Usuario> usuarioRespuesta = Optional.of(new Usuario());
        usuarioRespuesta = usuarioRepository.findById(id);
        Usuario usuarioExistente = usuarioRespuesta.get();
        respuesta = respuestaRepository.findById(usuarioExistente.getId());
        String token = respuesta.map(Respuesta::getToken).orElse(null);
        String activo = validarActivo(token, usuarioExistente);
        if (activo.equals("Activo")){
            Usuario usuario1 = actualizarDatosUsuario(usuario, usuarioExistente);
            token = jwtUtil.generateToken(usuario1);
            Respuesta respuestaExistente = respuesta.get();
            respuestaExistente.setId(respuestaExistente.getId());
            respuestaExistente.setCreado(respuestaExistente.getCreado());
            respuestaExistente.setModificado(fechaModificacionUsuario());
            respuestaExistente.setUltimoLogin(respuestaExistente.getUltimoLogin());
            respuestaExistente.setToken(token);
            respuestaExistente.setActivo(activo);
            respuestaExistente.setSesion(respuestaExistente.getSesion());
            saveRespuesta = respuestaRepository.save(respuestaExistente);
        }
        return saveRespuesta;
    }


    private Usuario actualizarDatosUsuario(Usuario usuario, Usuario usuarioExistente) {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(usuarioExistente.getId());
        usuarioActualizado.setNombre(usuario.getNombre());
        usuarioActualizado.setEmail(usuario.getEmail());
        usuarioActualizado.setContraseña(usuario.getContraseña());
        usuarioActualizado.setTelefonos(usuario.getTelefonos());
        usuarioRepository.save(usuarioActualizado);
        return usuarioActualizado;
    }
    @Override
    public Respuesta actualizarContraseña(Long id, Map<String, String> request) {
        Optional<Respuesta> respuesta = Optional.of(new Respuesta());
        Respuesta saveRespuesta = new Respuesta();
        Optional<Usuario> usuarioRespuesta = Optional.of(new Usuario());
        usuarioRespuesta = usuarioRepository.findById(id);
        Usuario usuarioExistente = usuarioRespuesta.get();
        respuesta = respuestaRepository.findById(usuarioExistente.getId());
        String token = respuesta.map(Respuesta::getToken).orElse(null);
        String activo = validarActivo(token, usuarioExistente);
        if (activo.equals("Activo") ){
            Usuario usuario1 = actualizarPasswordUsuario(request, usuarioExistente);
            token = jwtUtil.generateToken(usuario1);
            Respuesta respuestaExistente = respuesta.get();
            respuestaExistente.setId(respuestaExistente.getId());
            respuestaExistente.setCreado(respuestaExistente.getCreado());
            respuestaExistente.setModificado(fechaModificacionUsuario());
            respuestaExistente.setUltimoLogin(respuestaExistente.getUltimoLogin());
            respuestaExistente.setToken(token);
            respuestaExistente.setActivo(activo);
            respuestaExistente.setSesion(respuestaExistente.getSesion());
            saveRespuesta = respuestaRepository.save(respuestaExistente);
        }
        return saveRespuesta;
    }

    private Usuario actualizarPasswordUsuario(Map<String, String> request, Usuario usuarioExistente) {
        Usuario usuarioActualizado = new Usuario();
        String nuevaContraseña = request.get("contraseña");
        usuarioActualizado.setId(usuarioExistente.getId());
        usuarioActualizado.setNombre(usuarioExistente.getNombre());
        usuarioActualizado.setEmail(usuarioExistente.getEmail());
        usuarioActualizado.setContraseña(nuevaContraseña);
        usuarioActualizado.setTelefonos(usuarioExistente.getTelefonos());
        usuarioRepository.save(usuarioActualizado);
        return usuarioActualizado;
    }


    private String validarActivo(String token, Usuario usuarioExistente) {
        try {
            boolean esValido = jwtUtil.isTokenValid(token,usuarioExistente );
            String respuesta ;
            if (esValido == true){
                return respuesta = "Activo";
            }else {
                return respuesta = "No Activo";
            }
        }catch (UsuarioException e) {
            LOGGER.error("Error en la validación del usuario", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Mensaje de error", e.getMessage(), e);
            throw new UsuarioException("El token ya no es valido");
        }
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
