package com.example.SistemaDePedidos.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SistemaDePedidos.model.Estado;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.crearPedido(pedido);
    }

    @GetMapping("/todos")
    public List<Pedido> listarTodosLosPedidos() {
        return pedidoService.listarTodosLosPedidos();
    }

    @GetMapping("/por-usuario/{idUsuario}")
    public List<Pedido> listarPedidosPorUsuario(@PathVariable String idUsuario) {
        return pedidoService.listarPedidosPorUsuario(idUsuario);
    }

    @GetMapping("/por-estado/{estado}")
    public List<Pedido> listarPedidosPorEstado(@PathVariable Estado estado) {
        return pedidoService.listarPedidosPorEstado(estado);
    }

    @GetMapping("/{codigoPedido}")
    public Pedido obtenerPedido(@PathVariable String codigoPedido) {
        return pedidoService.obtenerPedido(codigoPedido);
    }

    @GetMapping("/{codigoPedido}/total")
    public double calcularTotalPedido(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.obtenerPedido(codigoPedido);
        return pedido != null ? pedidoService.calcularTotalPedido(pedido) : 0.0;
    }

    @PutMapping("/{codigoPedido}/estado")
    public Pedido actualizarEstadoPedido(@PathVariable String codigoPedido, @RequestParam Estado nuevoEstado) {
        return pedidoService.cambiarEstadoPedido(codigoPedido, nuevoEstado);
    }

    @PostMapping("/{codigoPedido}/items")
    public Pedido agregarItemAlPedido(
            @PathVariable String codigoPedido,
            @RequestParam String idItem,
            @RequestParam(defaultValue = "1") int cantidad,
            @RequestParam(defaultValue = "") String observaciones) {
        return pedidoService.agregarItemAlPedido(codigoPedido, idItem, cantidad, observaciones);
    }

    @DeleteMapping("/{codigoPedido}/items/{idDetalle}")
    public Pedido eliminarItemDelPedido(@PathVariable String codigoPedido, @PathVariable String idDetalle) {
        return pedidoService.eliminarItemDelPedido(codigoPedido, idDetalle);
    }
    
    @PutMapping("/{codigoPedido}/items/{idDetalle}")
    public Pedido actualizarCantidadDetalle(
            @PathVariable String codigoPedido,
            @PathVariable String idDetalle,
            @RequestParam int cantidad) {
        return pedidoService.actualizarCantidadDetalle(codigoPedido, idDetalle, cantidad);
    }

    @DeleteMapping("/{codigoPedido}/cancelar")
    public void cancelarPedido(@PathVariable String codigoPedido) {
        pedidoService.cambiarEstadoPedido(codigoPedido, Estado.CANCELADO);
    }
}
