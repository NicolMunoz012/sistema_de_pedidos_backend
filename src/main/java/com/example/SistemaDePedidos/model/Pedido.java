package com.example.SistemaDePedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "pedidos")
public class Pedido {
    @Id
    private int codigoPedido;
    private Cliente cliente;
    private Date fecha;
    private Estado estado;
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(int codigoPedido, Cliente cliente, Date fecha, Estado estado, List<DetallePedido> detalles) {
        this.codigoPedido = codigoPedido;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.detalles = detalles;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
                ", cliente=" + cliente +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", detalles=" + detalles +
                '}';
    }
}
