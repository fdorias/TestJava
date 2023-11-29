package com.pruebaspring.prueba.services;

import com.pruebaspring.prueba.exception.UsuarioException;
import com.pruebaspring.prueba.model.Usuario;
import com.pruebaspring.prueba.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pruebaspring.prueba.constans.Constantes.MENSAJE;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);


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

    @Override
    public Usuario save(Usuario usuario) {
        List<Usuario> listUsuario = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        boolean validacion;
        listUsuario = usuarioRepository.findAll();
        try {
            if (validarFormatoCorreo(usuario.getEmail())){
                if (listUsuario.isEmpty()){
                    return usuario1 = usuarioRepository.save(usuario);
                }else{
                    validacion = validarUsuario(listUsuario, usuario);
                    if (validacion== false){
                        return usuario1 = usuarioRepository.save(usuario);
                    }
                }
                throw new UsuarioException("Usuario ya se encuentra registrado");
            }else {
                throw new UsuarioException("Error en formato Email");
            }
        } catch (UsuarioException e) {
            LOGGER.error("Error en la validación del usuario", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Mensaje de error", e.getMessage(), e);
            throw new UsuarioException("Error inesperado al procesar el usuario");
        }
    }

    public boolean validarUsuario (List<Usuario> listUsuario, Usuario usuario){
        boolean respuesta = false;
        for (Usuario user : listUsuario){
            if ( user.getEmail().equals(usuario.getEmail())){
                respuesta = true;
            }else{
                respuesta = false;
            }
        }
        return respuesta;
    }

    private boolean validarFormatoCorreo(String correo) {
        String formatoCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(formatoCorreo);
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }
}
