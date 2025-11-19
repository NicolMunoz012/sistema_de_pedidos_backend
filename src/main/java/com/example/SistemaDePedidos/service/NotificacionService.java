package com.example.SistemaDePedidos.service;

import com.example.SistemaDePedidos.model.Cliente;
import com.example.SistemaDePedidos.model.Estado;
import com.example.SistemaDePedidos.model.Factura;
import com.example.SistemaDePedidos.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public void enviarNotificacionPedidoCreado(Pedido pedido, Cliente cliente) {
        String mensaje = generarMensajePedido(pedido);
        enviarCorreo(cliente.getGmail(), "Pedido Creado", mensaje);
    }

    public void enviarNotificacionCambioEstado(Pedido pedido, Estado nuevoEstado) {
        String mensaje = "Su pedido #" + pedido.getCodigoPedido() + " cambió a estado: " + nuevoEstado;
        enviarCorreo(pedido.getCliente().getGmail(), "Cambio de Estado", mensaje);
    }

    public void enviarNotificacionFacturaGenerada(Factura factura, Cliente cliente) {
        String mensaje = generarMensajeFactura(factura);
        enviarCorreo(cliente.getGmail(), "Factura Generada", mensaje);
    }

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        System.out.println("Enviando correo a: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Cuerpo: " + cuerpo);
    }

    public void enviarSMS(String numeroTelefono, String mensaje) {
        System.out.println("Enviando SMS a: " + numeroTelefono);
        System.out.println("Mensaje: " + mensaje);
    }

    public String generarMensajePedido(Pedido pedido) {
        return "Pedido #" + pedido.getCodigoPedido() + " creado exitosamente. Estado: " + pedido.getEstado();
    }

    public String generarMensajeFactura(Factura factura) {
        return "Factura #" + factura.getCodigoFactura() + " generada. Monto total: $" + factura.getMontoTotal();
    }
}
