package com.example.SistemaDePedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
@TypeAlias("usuario")
public class Usuario {
    @Id
    private String idUsuario;
    private String nombre;
    private String gmail;
    private String contraseña;
    private RolUsuario rol;
    private String direccion;

    public Usuario() {
    }

    public Usuario(String idUsuario, String nombre, String gmail, String contraseña, RolUsuario rol, String direccion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.gmail = gmail;
        this.contraseña = contraseña;
        this.rol = rol;
        this.direccion = direccion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", gmail='" + gmail + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
