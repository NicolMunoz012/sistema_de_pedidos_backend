package com.example.SistemaDePedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Cliente;
import com.example.SistemaDePedidos.model.RolUsuario;
import com.example.SistemaDePedidos.model.Usuario;
import com.example.SistemaDePedidos.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        if (!verificarEmailExiste(usuario.getGmail())) {
            usuario.setContraseña(encriptarContraseña(usuario.getContraseña()));
            if (usuario.getRol() == null) {
                usuario.setRol(RolUsuario.CLIENTE);
            }
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("El email " + usuario.getGmail() + " ya está registrado."); 
    }

    public Usuario obtenerUsuario(String idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
    
    // ✅ AGREGAR ESTE MÉTODO
    public Cliente obtenerCliente(String idUsuario) {
        return usuarioRepository.findClienteById(idUsuario).orElse(null);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(String idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public boolean validarCredenciales(String gmail, String contraseña) {
        Usuario usuario = usuarioRepository.findByGmail(gmail).orElse(null);
        return usuario != null && usuario.getContraseña().equals(encriptarContraseña(contraseña));
    }

    public Usuario autenticarUsuario(String gmail, String contraseña) {
        if (validarCredenciales(gmail, contraseña)) {
            return usuarioRepository.findByGmail(gmail).orElse(null);
        }
        return null;
    }

    public void recuperarContraseña(String gmail) {
        Usuario usuario = usuarioRepository.findByGmail(gmail).orElse(null);
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
        return usuarioRepository.findByGmail(gmail).isPresent();
    }

    public String encriptarContraseña(String contraseña) {
        return Integer.toHexString(contraseña.hashCode());
    }
}