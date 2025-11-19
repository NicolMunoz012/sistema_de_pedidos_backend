package com.restaurante.pedidos.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class Cliente extends Usuario {
    private String direccion;

    public Cliente() {
    }

    public Cliente(String idUsuario, String nombre, String gmail, String contraseña, String direccion) {
        super(idUsuario, nombre, gmail, contraseña);
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
