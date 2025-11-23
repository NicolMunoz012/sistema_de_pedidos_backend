package com.example.SistemaDePedidos.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Factura;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.repository.FacturaRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private NotificacionService notificacionService;

    public Factura generarFactura(Pedido pedido) {
        Factura factura = new Factura();
        factura.setFechaEmision(new Date());
        factura.setMontoTotal(calcularMontoTotal(pedido));
        factura.setMetodoPago("Efectivo");
        Factura nuevaFactura = facturaRepository.save(factura);
        notificacionService.enviarNotificacionFacturaGenerada(nuevaFactura, pedido.getIdUsuario());
        return nuevaFactura;
    }

    public Factura obtenerFactura(String codigoFactura) {
        return facturaRepository.findById(codigoFactura).orElse(null);
    }

    public List<Factura> listarTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> listarFacturasPorUsuario(String idUsuario) {
        return facturaRepository.findAll();
    }

    public List<Factura> listarFacturasPorRangoFechas(Date fechaInicio, Date fechaFin) {
        return facturaRepository.findAll().stream()
                .filter(f -> !f.getFechaEmision().before(fechaInicio) && !f.getFechaEmision().after(fechaFin))
                .collect(Collectors.toList());
    }

    public double calcularMontoTotal(Pedido pedido) {
        if (pedido.getDetalles() == null) {
            return 0.0;
        }
        return pedido.getDetalles().stream()
                .mapToDouble(d -> d.getSubtotal())
                .sum();
    }

    public Factura aplicarDescuento(Factura factura, double porcentajeDescuento) {
        double descuento = factura.getMontoTotal() * (porcentajeDescuento / 100);
        factura.setMontoTotal(factura.getMontoTotal() - descuento);
        return facturaRepository.save(factura);
    }

    public boolean validarMetodoPago(String metodoPago) {
        return metodoPago != null && !metodoPago.isEmpty();
    }



    public void enviarFacturaPorCorreo(Factura factura) {
        System.out.println("Enviando factura #" + factura.getCodigoFactura() + " por correo");
    }
}
