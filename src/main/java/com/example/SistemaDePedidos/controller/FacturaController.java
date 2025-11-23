package com.example.SistemaDePedidos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SistemaDePedidos.model.Factura;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.service.FacturaService;
import com.example.SistemaDePedidos.service.PedidoService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/generar/{codigoPedido}")
    public Factura generarFactura(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.obtenerPedido(codigoPedido);
        return pedido != null ? facturaService.generarFactura(pedido) : null;
    }

    @GetMapping("/{codigoFactura}")
    public Factura obtenerFactura(@PathVariable String codigoFactura) {
        return facturaService.obtenerFactura(codigoFactura);
    }

    @GetMapping
    public List<Factura> listarTodasLasFacturas() {
        return facturaService.listarTodasLasFacturas();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Factura> listarFacturasPorUsuario(@PathVariable String idUsuario) {
        return facturaService.listarFacturasPorUsuario(idUsuario);
    }

@GetMapping("/rango")
public List<Factura> listarFacturasPorFecha(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {

    return facturaService.listarFacturasPorRangoFechas(fechaInicio, fechaFin);
}

    @GetMapping("/{codigoFactura}/detalle")
    public Factura obtenerDetalleFactura(@PathVariable String codigoFactura) {
        return facturaService.obtenerFactura(codigoFactura);
    }
}
