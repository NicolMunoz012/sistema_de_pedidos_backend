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
import com.example.SistemaDePedidos.model.Usuario;
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

    /**
     * Crea un nuevo pedido congelando los precios de los items al momento de la creación.
     * Los detalles son documentos embebidos (composición fuerte).
     */
    public Pedido crearPedido(Pedido pedido) {
        // Verificar que el usuario existe
        if (pedido.getIdUsuario() != null) {
            Usuario usuario = usuarioService.obtenerUsuario(pedido.getIdUsuario());
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado con ID: " + pedido.getIdUsuario());
            }
        }
        
        // ✅ Rellenar datos completos de los Items y congelar precios
        if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
            int detalleIndex = 1;
            for (DetallePedido detalle : pedido.getDetalles()) {
                if (detalle.getItem() != null && detalle.getItem().getIdItem() != null) {
                    String idItem = detalle.getItem().getIdItem();
                    Item itemCompleto = itemService.obtenerItem(idItem);
                    if (itemCompleto == null) {
                        throw new RuntimeException("Item no encontrado con ID: " + idItem);
                    }
                    
                    // ✅ Asignar item completo
                    detalle.setItem(itemCompleto);
                    
                    // ✅ Generar ID del detalle si no existe
                    if (detalle.getIdDetalle() == null || detalle.getIdDetalle().isEmpty()) {
                        detalle.setIdDetalle(String.valueOf(detalleIndex++));
                    }
                    
                    // ✅ Congelar precio unitario del item en este momento
                    detalle.setPrecioUnitario(itemCompleto.getPrecio());
                    
                    // ✅ Calcular subtotal automáticamente
                    detalle.calcularSubtotal();
                }
            }
        }
        
        pedido.setFecha(new Date());
        pedido.setEstado(Estado.PENDIENTE);
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        notificacionService.enviarNotificacionPedidoCreado(nuevoPedido, pedido.getIdUsuario());
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

    public List<Pedido> listarPedidosPorUsuario(String idUsuario) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getIdUsuario() != null && p.getIdUsuario().equals(idUsuario))
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

    /**
     * Agrega un item existente al pedido creando un nuevo DetallePedido.
     * El precio se congela al momento de agregar el item.
     * 
     * Flujo:
     * 1. Buscar el Pedido por codigoPedido
     * 2. Buscar el Item por idItem
     * 3. Crear un nuevo DetallePedido con ese item
     * 4. Setear precioUnitario = item.getPrecio() (congelado)
     * 5. Setear subtotal = cantidad * precioUnitario
     * 6. Agregar el detalle al Pedido
     * 7. Guardar el Pedido completo
     */
    public Pedido agregarItemAlPedido(String codigoPedido, String idItem, int cantidad, String observaciones) {
        // 1. Buscar el Pedido
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado con código: " + codigoPedido);
        }
        
        // 2. Buscar el Item
        Item item = itemService.obtenerItem(idItem);
        if (item == null) {
            throw new RuntimeException("Item no encontrado con ID: " + idItem);
        }
        
        // Validar que el item esté disponible
        if (!item.isDisponibilidad()) {
            throw new RuntimeException("El item '" + item.getNombre() + "' no está disponible");
        }
        
        // Validar cantidad
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        
        // 3. Crear un nuevo DetallePedido
        // ✅ El constructor congela el precio automáticamente
        DetallePedido detalle = new DetallePedido(
            generarIdDetalle(pedido),
            item,
            cantidad,
            observaciones
        );
        
        // 4-5. El precio y subtotal ya se setean en el constructor
        // precioUnitario = item.getPrecio() (congelado)
        // subtotal = cantidad * precioUnitario
        
        // 6. Agregar el detalle al Pedido
        if (pedido.getDetalles() == null) {
            pedido.setDetalles(new ArrayList<>());
        }
        pedido.getDetalles().add(detalle);
        
        // 7. Guardar el Pedido completo
        return pedidoRepository.save(pedido);
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

    /**
     * Elimina un detalle del pedido por su ID.
     */
    public Pedido eliminarItemDelPedido(String codigoPedido, String idDetalle) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado con código: " + codigoPedido);
        }
        
        if (pedido.getDetalles() != null) {
            boolean eliminado = pedido.getDetalles().removeIf(d -> d.getIdDetalle().equals(idDetalle));
            if (!eliminado) {
                throw new RuntimeException("Detalle no encontrado con ID: " + idDetalle);
            }
            return pedidoRepository.save(pedido);
        }
        return pedido;
    }
    
    /**
     * Actualiza la cantidad de un detalle existente.
     * El subtotal se recalcula automáticamente.
     */
    public Pedido actualizarCantidadDetalle(String codigoPedido, String idDetalle, int cantidad) {
        Pedido pedido = obtenerPedido(codigoPedido);
        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado con código: " + codigoPedido);
        }
        
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        
        if (pedido.getDetalles() != null) {
            DetallePedido detalle = pedido.getDetalles().stream()
                .filter(d -> d.getIdDetalle().equals(idDetalle))
                .findFirst()
                .orElse(null);
            
            if (detalle == null) {
                throw new RuntimeException("Detalle no encontrado con ID: " + idDetalle);
            }
            
            // ✅ Al cambiar la cantidad, el subtotal se recalcula automáticamente
            detalle.setCantidad(cantidad);
            
            return pedidoRepository.save(pedido);
        }
        return pedido;
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