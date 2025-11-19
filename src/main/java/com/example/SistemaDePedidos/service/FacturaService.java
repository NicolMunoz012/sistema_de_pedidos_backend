package com.example.SistemaDePedidos.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Factura;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.repository.FacturaRepository;
import com.example.SistemaDePedidos.repository.PedidoRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private NotificacionService notificacionService;

    public Factura generarFactura(Pedido pedido) {
        Factura factura = new Factura();
        factura.setCodigoFactura(generarCodigoFactura());
        factura.setFechaEmision(new Date());
        factura.setMontoTotal(calcularMontoTotal(pedido));
        factura.setMetodoPago("Efectivo");
        Factura nuevaFactura = facturaRepository.save(factura);
        notificacionService.enviarNotificacionFacturaGenerada(nuevaFactura, pedido.getCliente());
        return nuevaFactura;
    }

    public Factura obtenerFactura(int codigoFactura) {
        return facturaRepository.findById(codigoFactura).orElse(null);
    }

    public List<Factura> listarTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> listarFacturasPorCliente(String idCliente) {
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

    public int generarCodigoFactura() {
        List<Factura> facturas = facturaRepository.findAll();
        return facturas.isEmpty() ? 1 : facturas.size() + 1;
    }

    public void enviarFacturaPorCorreo(Factura factura) {
        System.out.println("Enviando factura #" + factura.getCodigoFactura() + " por correo");
    }
}
