package com.example.SistemaDePedidos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.DetallePedido;
import com.example.SistemaDePedidos.model.Estado;
import com.example.SistemaDePedidos.model.Item;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private NotificacionService notificacionService;

    public Pedido crearPedido(Pedido pedido) {
        pedido.setFecha(new Date());
        pedido.setEstado(Estado.PENDIENTE);
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        notificacionService.enviarNotificacionPedidoCreado(nuevoPedido, pedido.getCliente());
        return nuevoPedido;
    }

    public Pedido obtenerPedido(int codigoPedido) {
        return pedidoRepository.findById(codigoPedido).orElse(null);
    }

    public Pedido actualizarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(int codigoPedido) {
        pedidoRepository.deleteById(codigoPedido);
    }

    public List<Pedido> listarTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosPorCliente(String idCliente) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getCliente().getIdUsuario().equals(idCliente))
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosPorEstado(Estado estado) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public Pedido cambiarEstadoPedido(int codigoPedido, Estado nuevoEstado) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido != null) {
            pedido.setEstado(nuevoEstado);
            Pedido actualizado = pedidoRepository.save(pedido);
            notificarCambioEstado(actualizado);
            return actualizado;
        }
        return null;
    }

    public Pedido agregarItemAlPedido(int codigoPedido, Item item) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido != null && item.isDisponibilidad()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setIdDetalle(generarIdDetalle(pedido));
            detalle.setItem(item);
            detalle.setCantidad(1);
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setSubtotal(item.getPrecio());
            detalle.setObservaciones("");
            
            if (pedido.getDetalles() == null) {
                pedido.setDetalles(new ArrayList<>());
            }
            pedido.getDetalles().add(detalle);
            return pedidoRepository.save(pedido);
        }
        return null;
    }
    
    private int generarIdDetalle(Pedido pedido) {
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            return 1;
        }
        return pedido.getDetalles().stream()
                .mapToInt(DetallePedido::getIdDetalle)
                .max()
                .orElse(0) + 1;
    }

    public Pedido eliminarItemDelPedido(int codigoPedido, String nombreItem) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido != null && pedido.getDetalles() != null) {
            pedido.getDetalles().removeIf(d -> d.getItem().getNombre().equals(nombreItem));
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    public double calcularTotalPedido(Pedido pedido) {
        if (pedido.getDetalles() == null) {
            return 0.0;
        }
        return pedido.getDetalles().stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public boolean validarItemsDisponibles(List<Item> items) {
        return items.stream().allMatch(Item::isDisponibilidad);
    }

    public void notificarCambioEstado(Pedido pedido) {
        notificacionService.enviarNotificacionCambioEstado(pedido, pedido.getEstado());
    }
}
