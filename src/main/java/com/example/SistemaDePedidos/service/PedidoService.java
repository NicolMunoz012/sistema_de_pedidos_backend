package com.example.SistemaDePedidos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Cliente;
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
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ItemService itemService;

    public Pedido crearPedido(Pedido pedido) {
     
        if (pedido.getCliente() != null && pedido.getCliente().getIdUsuario() != null) {
            String idCliente = pedido.getCliente().getIdUsuario();
            Cliente clienteCompleto = usuarioService.obtenerCliente(idCliente);  // ✅ CAMBIO AQUÍ
            if (clienteCompleto != null) {
                pedido.setCliente(clienteCompleto);
            } else {
                throw new RuntimeException("Cliente con ID " + idCliente + " no encontrado");
            }
        }
        
        // Rellenar datos completos de los Items en los detalles
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                if (detalle.getItem() != null && detalle.getItem().getIdItem() != null) {
                    String idItem = detalle.getItem().getIdItem();
                    Item itemCompleto = itemService.obtenerItem(idItem);
                    if (itemCompleto != null) {
                        detalle.setItem(itemCompleto);
                        detalle.setPrecioUnitario(itemCompleto.getPrecio());
                        detalle.setSubtotal(itemCompleto.getPrecio() * detalle.getCantidad());
                    } else {
                        throw new RuntimeException("Item con ID " + idItem + " no encontrado");
                    }
                }
            }
        }
        
        pedido.setFecha(new Date());
        pedido.setEstado(Estado.PENDIENTE);
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        notificacionService.enviarNotificacionPedidoCreado(nuevoPedido, pedido.getCliente());
        return nuevoPedido;
    }

    public Pedido obtenerPedido(String codigoPedido) {
        return pedidoRepository.findById(codigoPedido).orElse(null);
    }

    public Pedido actualizarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(String codigoPedido) {
        pedidoRepository.deleteById(codigoPedido);
    }

    public List<Pedido> listarTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosPorCliente(String idCliente) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getIdUsuario().equals(idCliente))
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPedidosPorEstado(Estado estado) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public Pedido cambiarEstadoPedido(String codigoPedido, Estado nuevoEstado) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido != null) {
            pedido.setEstado(nuevoEstado);
            Pedido actualizado = pedidoRepository.save(pedido);
            notificarCambioEstado(actualizado);
            return actualizado;
        }
        return null;
    }

    public Pedido agregarItemAlPedido(String codigoPedido, Item item) {
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
    
    private String generarIdDetalle(Pedido pedido) {
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            return "1";
        }
        int maxId = pedido.getDetalles().stream()
                .map(DetallePedido::getIdDetalle)
                .filter(id -> id != null && id.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
        return String.valueOf(maxId + 1);
    }

    public Pedido eliminarItemDelPedido(String codigoPedido, String nombreItem) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido != null && pedido.getDetalles() != null) {
            pedido.getDetalles().removeIf(d -> d.getItem().getNombre().equals(nombreItem));
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    public double calcularTotalPedido(Pedido pedido) {
        if (pedido == null || pedido.getDetalles() == null) {
            return 0.0;
        }
        return pedido.getDetalles().stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public boolean validarItemsDisponibles(List<Item> items) {
        return items != null && items.stream().allMatch(Item::isDisponibilidad);
    }

    public void notificarCambioEstado(Pedido pedido) {
        if (pedido != null) {
            notificacionService.enviarNotificacionCambioEstado(pedido, pedido.getEstado());
        }
    }
}