package com.pruebaspring.prueba.services;

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
            if (validarFormatoCorreo(usuario.getCorreo())){
                if (listUsuario.isEmpty()){
                    usuario1 = usuarioRepository.save(usuario);
                }else{
                    validacion = validarUsuario(listUsuario, usuario);
                    if (validacion== false){
                        usuario1 = usuarioRepository.save(usuario);
                    }
                }
            }else {
                LOGGER.error("mensaje de error");
            }
        }catch (Exception e){
            LOGGER.error("mensaje de error",e.getMessage(),e);
        }
        return usuario1;
    }

    public boolean validarUsuario (List<Usuario> listUsuario, Usuario usuario){
        boolean respuesta = false;
        for (Usuario user : listUsuario){
            if ( user.getCorreo().equals(usuario.getCorreo())){
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
