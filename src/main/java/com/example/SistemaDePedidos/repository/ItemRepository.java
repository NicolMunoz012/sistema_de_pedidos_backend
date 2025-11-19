package com.example.SistemaDePedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SistemaDePedidos.model.Categoria;
import com.example.SistemaDePedidos.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    
    // Métodos personalizados con nomenclatura de Spring Data
    Optional<Item> findByNombre(String nombre);
    
    List<Item> findByCategoria(Categoria categoria);
    
    List<Item> findByDisponibilidad(boolean disponibilidad);
    
    List<Item> findByNombreContainingIgnoreCase(String nombre);
    
    boolean existsByNombre(String nombre);
}