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
import com.example.SistemaDePedidos.model.Item;
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

    @GetMapping("/{codigoPedido}")
    public Pedido obtenerPedido(@PathVariable String codigoPedido) {
        return pedidoService.obtenerPedido(codigoPedido);
    }

    @GetMapping
    public List<Pedido> listarTodosLosPedidos() {
        return pedidoService.listarTodosLosPedidos();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> listarPedidosPorCliente(@PathVariable String idCliente) {
        return pedidoService.listarPedidosPorCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    public List<Pedido> listarPedidosPorEstado(@PathVariable Estado estado) {
        return pedidoService.listarPedidosPorEstado(estado);
    }

    @PutMapping("/{codigoPedido}/estado")
    public Pedido actualizarEstadoPedido(@PathVariable String codigoPedido, @RequestParam Estado nuevoEstado) {
        return pedidoService.cambiarEstadoPedido(codigoPedido, nuevoEstado);
    }

    @PostMapping("/{codigoPedido}/items")
    public Pedido agregarItemAlPedido(@PathVariable String codigoPedido, @RequestBody Item item) {
        return pedidoService.agregarItemAlPedido(codigoPedido, item);
    }

    @DeleteMapping("/{codigoPedido}/items/{nombreItem}")
    public Pedido eliminarItemDelPedido(@PathVariable String codigoPedido, @PathVariable String nombreItem) {
        return pedidoService.eliminarItemDelPedido(codigoPedido, nombreItem);
    }

    @GetMapping("/{codigoPedido}/total")
    public double calcularTotalPedido(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.obtenerPedido(codigoPedido);
        return pedido != null ? pedidoService.calcularTotalPedido(pedido) : 0.0;
    }

    @DeleteMapping("/{codigoPedido}/cancelar")
    public void cancelarPedido(@PathVariable String codigoPedido) {
        pedidoService.cambiarEstadoPedido(codigoPedido, Estado.CANCELADO);
    }
}
