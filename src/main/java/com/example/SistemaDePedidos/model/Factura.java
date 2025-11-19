package com.example.SistemaDePedidos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facturas")
public class Factura {
    @Id
    private String codigoFactura;
    private Date fechaEmision;
    private double montoTotal;
    private String metodoPago;

    public Factura() {
    }

    public Factura(String codigoFactura, Date fechaEmision, double montoTotal, String metodoPago) {
        this.codigoFactura = codigoFactura;
        this.fechaEmision = fechaEmision;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
    }

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(String codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "codigoFactura=" + codigoFactura +
                ", fechaEmision=" + fechaEmision +
                ", montoTotal=" + montoTotal +
                ", metodoPago='" + metodoPago + '\'' +
                '}';
    }
}
