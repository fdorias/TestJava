package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.exception.UsuarioException;
import com.pruebaspring.prueba.model.Login;
import com.pruebaspring.prueba.model.Respuesta;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.LoginRepository;
import com.pruebaspring.prueba.repository.RespuestaRepository;
import com.pruebaspring.prueba.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class LoginServicesImpl implements LoginService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public Respuesta validarUsuario(Login login) {
        try {
            Usuario usuario = new Usuario();
            Respuesta saveRespuesta = new Respuesta();
            usuario.setEmail(login.getEmail());
            usuario = loginRepository.findByEmail(login.getEmail());
            if (usuario!= null){
                Optional<Respuesta> respuesta = Optional.of(new Respuesta());
                if (usuario.getContraseña().equals(login.getPassword())){
                    respuesta = respuestaRepository.findById(usuario.getId());
                    String token = respuesta.map(Respuesta::getToken).orElse(null);
                    String activo = validarActivo(token, usuario);
                    if(activo.equals("Activo")){
                        Respuesta respuestaExistente = respuesta.get();
                        respuestaExistente.setId(respuestaExistente.getId());
                        respuestaExistente.setCreado(respuestaExistente.getCreado());
                        respuestaExistente.setModificado(respuestaExistente.getModificado());
                        respuestaExistente.setUltimoLogin(fechaUltimoLogin());
                        respuestaExistente.setToken(token);
                        respuestaExistente.setActivo(activo);
                        if (respuestaExistente.getSesion() == false){
                            respuestaExistente.setSesion(true);
                            saveRespuesta = respuestaRepository.save(respuestaExistente);
                            return saveRespuesta;
                        }else {
                            throw new UsuarioException("La sesion ya esta activa");
                        }
                    }else {
                        throw new UsuarioException("El token ya no es valido");
                    }
                }else {
                    throw new UsuarioException("Contraseña incorrecta");
                }
            }else {
                throw new UsuarioException("Usuario no existe");
            }
        }catch (UsuarioException e) {
            LOGGER.error("Error en la validación del usuario", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Mensaje de error", e.getMessage(), e);
            throw new UsuarioException("Error inesperado al procesar el usuario");
        }

    }

    @Override
    public Respuesta logout(Login login) {
        Usuario usuario = new Usuario();
        Respuesta saveRespuesta = new Respuesta();
        usuario.setEmail(login.getEmail());
        usuario = loginRepository.findByEmail(login.getEmail());
        Optional<Respuesta> respuesta = Optional.of(new Respuesta());
        respuesta = respuestaRepository.findById(usuario.getId());
        String token = respuesta.map(Respuesta::getToken).orElse(null);
        String activo = validarActivo(token, usuario);
        if(activo == "Activo"){
            Respuesta respuestaExistente = respuesta.get();
            respuestaExistente.setId(respuestaExistente.getId());
            respuestaExistente.setCreado(respuestaExistente.getCreado());
            respuestaExistente.setModificado(respuestaExistente.getModificado());
            respuestaExistente.setUltimoLogin(fechaUltimoLogin());
            respuestaExistente.setToken(token);
            respuestaExistente.setActivo(activo);
            respuestaExistente.setSesion(false);
            saveRespuesta = respuestaRepository.save(respuestaExistente);
        }
        return saveRespuesta;
    }

    private String fechaUltimoLogin(){
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = fechaActual.format(formatoFecha);
        return fecha;
    }

    private String validarActivo(String token, Usuario usuario) {
        try {
            boolean esValido = jwtUtil.isTokenValid(token,usuario );
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
}
