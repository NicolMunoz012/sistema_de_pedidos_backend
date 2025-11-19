package com.example.SistemaDePedidos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SistemaDePedidos.model.Factura;

@Repository
public interface FacturaRepository extends MongoRepository<Factura, String> {
    
    List<Factura> findByFechaEmisionBetween(Date fechaInicio, Date fechaFin);
}