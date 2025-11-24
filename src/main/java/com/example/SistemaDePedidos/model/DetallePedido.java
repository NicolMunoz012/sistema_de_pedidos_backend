package com.example.SistemaDePedidos.model;

public class DetallePedido {
    private String idDetalle;
    private Item item; 
    private int cantidad;
    private double precioUnitario; 
    private double subtotal; 
    private String observaciones;

    public DetallePedido() {
    }

 
    public DetallePedido(String idDetalle, Item item, int cantidad, String observaciones) {
        this.idDetalle = idDetalle;
        this.item = item;
        this.cantidad = cantidad;
        this.precioUnitario = item != null ? item.getPrecio() : 0.0;
        this.subtotal = this.precioUnitario * cantidad;
        this.observaciones = observaciones;
    }

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.precioUnitario * cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "idDetalle='" + idDetalle + '\'' +
                ", item=" + (item != null ? item.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
