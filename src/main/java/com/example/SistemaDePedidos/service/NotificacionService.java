package com.example.SistemaDePedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Estado;
import com.example.SistemaDePedidos.model.Factura;
import com.example.SistemaDePedidos.model.Pedido;
import com.example.SistemaDePedidos.model.Usuario;

@Service
public class NotificacionService {
    
    @Autowired
    private UsuarioService usuarioService;

    public void enviarNotificacionPedidoCreado(Pedido pedido, String idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
        if (usuario != null) {
            String mensaje = generarMensajePedido(pedido);
            enviarCorreo(usuario.getGmail(), "Pedido Creado", mensaje);
        }
    }

    public void enviarNotificacionCambioEstado(Pedido pedido, Estado nuevoEstado) {
        Usuario usuario = usuarioService.obtenerUsuario(pedido.getIdUsuario());
        if (usuario != null) {
            String mensaje = "Su pedido #" + pedido.getCodigoPedido() + " cambió a estado: " + nuevoEstado;
            enviarCorreo(usuario.getGmail(), "Cambio de Estado", mensaje);
        }
    }

    public void enviarNotificacionFacturaGenerada(Factura factura, String idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuario(idUsuario);
        if (usuario != null) {
            String mensaje = generarMensajeFactura(factura);
            enviarCorreo(usuario.getGmail(), "Factura Generada", mensaje);
        }
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
