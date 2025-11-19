package com.example.SistemaDePedidos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SistemaDePedidos.model.Estado;
import com.example.SistemaDePedidos.model.Pedido;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, Integer> {
    
    List<Pedido> findByCliente_IdUsuario(String idUsuario);
    
    List<Pedido> findByEstado(Estado estado);
    
    List<Pedido> findByFecha(Date fecha);
}