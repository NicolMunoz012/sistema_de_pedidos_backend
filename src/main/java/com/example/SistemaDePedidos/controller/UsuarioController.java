package com.example.SistemaDePedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SistemaDePedidos.model.Usuario;
import com.example.SistemaDePedidos.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario iniciarSesion(@RequestParam String gmail, @RequestParam String contraseña) {
        return usuarioService.autenticarUsuario(gmail, contraseña);
    }

    @PostMapping("/logout/{idUsuario}")
    public void cerrarSesion(@PathVariable String idUsuario) {
        System.out.println("Usuario " + idUsuario + " cerró sesión");
    }

    @PostMapping("/recuperar")
    public void recuperarContraseña(@RequestParam String gmail) {
        usuarioService.recuperarContraseña(gmail);
    }

    @GetMapping("/{idUsuario}")
    public Usuario obtenerUsuario(@PathVariable String idUsuario) {
        return usuarioService.obtenerUsuario(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuario actualizarUsuario(@PathVariable String idUsuario, @RequestBody Usuario usuario) {
        usuario.setIdUsuario(idUsuario);
        return usuarioService.actualizarUsuario(usuario);
    }

    @PutMapping("/{idUsuario}/cambiar-contraseña")
    public void cambiarContraseña(@PathVariable String idUsuario, 
                                   @RequestParam String contraseñaAntigua, 
                                   @RequestParam String contraseñaNueva) {
        usuarioService.cambiarContraseña(idUsuario, contraseñaAntigua, contraseñaNueva);
    }
}
