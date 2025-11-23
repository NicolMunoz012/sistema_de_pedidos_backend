package com.example.SistemaDePedidos.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String codigoPedido;
    private String idUsuario;
    private Date fecha;
    private Estado estado;
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(String codigoPedido, String idUsuario, Date fecha, Estado estado, List<DetallePedido> detalles) {
        this.codigoPedido = codigoPedido;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.estado = estado;
        this.detalles = detalles;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigoPedido=" + codigoPedido +
                ", idUsuario='" + idUsuario + '\'' +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", detalles=" + detalles +
                '}';
    }
}
