package com.example.SistemaDePedidos.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SistemaDePedidos.model.Cliente;
import com.example.SistemaDePedidos.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    Optional<Usuario> findByGmail(String gmail);
    
    boolean existsByGmail(String gmail);
    
    // ✅ AGREGAR ESTE MÉTODO
    @Query("{ '_id': ?0, '_class': 'com.example.SistemaDePedidos.model.Cliente' }")
    Optional<Cliente> findClienteById(String idUsuario);
}