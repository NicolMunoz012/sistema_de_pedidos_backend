package com.restaurante.pedidos.service;

import com.restaurante.pedidos.model.Usuario;
import com.restaurante.pedidos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        if (!verificarEmailExiste(usuario.getGmail())) {
            usuario.setContraseña(encriptarContraseña(usuario.getContraseña()));
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public Usuario obtenerUsuario(String idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(String idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public boolean validarCredenciales(String gmail, String contraseña) {
        Usuario usuario = usuarioRepository.findByGmail(gmail);
        return usuario != null && usuario.getContraseña().equals(encriptarContraseña(contraseña));
    }

    public Usuario autenticarUsuario(String gmail, String contraseña) {
        if (validarCredenciales(gmail, contraseña)) {
            return usuarioRepository.findByGmail(gmail);
        }
        return null;
    }

    public void recuperarContraseña(String gmail) {
        Usuario usuario = usuarioRepository.findByGmail(gmail);
        if (usuario != null) {
            System.out.println("Enviando correo de recuperación a: " + gmail);
        }
    }

    public void cambiarContraseña(String idUsuario, String contraseñaAntigua, String contraseñaNueva) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && usuario.getContraseña().equals(encriptarContraseña(contraseñaAntigua))) {
            usuario.setContraseña(encriptarContraseña(contraseñaNueva));
            usuarioRepository.save(usuario);
        }
    }

    public boolean verificarEmailExiste(String gmail) {
        return usuarioRepository.findByGmail(gmail) != null;
    }

    public String encriptarContraseña(String contraseña) {
        return Integer.toHexString(contraseña.hashCode());
    }
}
