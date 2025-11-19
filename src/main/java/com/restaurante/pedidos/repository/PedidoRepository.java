package com.restaurante.pedidos.repository;

import com.restaurante.pedidos.model.Estado;
import com.restaurante.pedidos.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, Integer> {
    Pedido guardar(Pedido pedido);
    Pedido buscarPorCodigo(int codigoPedido);
    List<Pedido> buscarPorCliente(String idCliente);
    List<Pedido> buscarPorEstado(Estado estado);
    List<Pedido> buscarPorFecha(Date fecha);
    List<Pedido> buscarTodos();
    Pedido actualizar(Pedido pedido);
    void eliminar(int codigoPedido);
    boolean existePorCodigo(int codigoPedido);
    int generarCodigoPedido();
}
