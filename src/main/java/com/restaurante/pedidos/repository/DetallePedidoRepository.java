package com.restaurante.pedidos.repository;

import com.restaurante.pedidos.model.DetallePedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends MongoRepository<DetallePedido, Integer> {
    DetallePedido guardar(DetallePedido detalle);
    DetallePedido buscarPorId(int idDetalle);
    List<DetallePedido> buscarPorPedido(int codigoPedido);
    DetallePedido actualizar(DetallePedido detalle);
    void eliminar(int idDetalle);
    List<DetallePedido> buscarTodos();
    boolean existePorId(int idDetalle);
}
