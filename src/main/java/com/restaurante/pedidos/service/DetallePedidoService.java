package com.restaurante.pedidos.service;

import com.restaurante.pedidos.model.DetallePedido;
import com.restaurante.pedidos.model.Item;
import com.restaurante.pedidos.repository.DetallePedidoRepository;
import com.restaurante.pedidos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ItemRepository itemRepository;

    public DetallePedido crearDetalle(Item item, int cantidad) {
        if (validarDisponibilidad(item, cantidad)) {
            DetallePedido detalle = new DetallePedido();
            detalle.setItem(item);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(congelarPrecio(item));
            detalle.setSubtotal(calcularSubtotal(detalle));
            return detallePedidoRepository.save(detalle);
        }
        return null;
    }

    public DetallePedido obtenerDetalle(int idDetalle) {
        return detallePedidoRepository.findById(idDetalle).orElse(null);
    }

    public DetallePedido actualizarDetalle(DetallePedido detalle) {
        detalle.setSubtotal(calcularSubtotal(detalle));
        return detallePedidoRepository.save(detalle);
    }

    public void eliminarDetalle(int idDetalle) {
        detallePedidoRepository.deleteById(idDetalle);
    }

    public List<DetallePedido> listarDetallesPorPedido(int codigoPedido) {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido modificarCantidad(int idDetalle, int nuevaCantidad) {
        DetallePedido detalle = obtenerDetalle(idDetalle);
        if (detalle != null && validarDisponibilidad(detalle.getItem(), nuevaCantidad)) {
            detalle.setCantidad(nuevaCantidad);
            detalle.setSubtotal(calcularSubtotal(detalle));
            return detallePedidoRepository.save(detalle);
        }
        return null;
    }

    public double calcularSubtotal(DetallePedido detalle) {
        return detalle.getPrecioUnitario() * detalle.getCantidad();
    }

    public boolean validarDisponibilidad(Item item, int cantidad) {
        return item != null && item.isDisponibilidad() && cantidad > 0;
    }

    public double congelarPrecio(Item item) {
        return item.getPrecio();
    }
}
