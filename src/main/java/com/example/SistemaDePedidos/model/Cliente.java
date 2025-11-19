package com.example.SistemaDePedidos.model;

import org.springframework.data.annotation.TypeAlias;

@TypeAlias("cliente")
public class Cliente extends Usuario {
    private String direccion;

    public Cliente() {
    }

    public Cliente(String idUsuario, String nombre, String gmail, String contraseña, String direccion) {
        super(idUsuario, nombre, gmail, contraseña, RolUsuario.CLIENTE);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "direccion='" + direccion + '\'' +
                ", " + super.toString() +
                '}';
    }
}
