package com.example.SistemaDePedidos.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SistemaDePedidos.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    Optional<Usuario> findByGmail(String gmail);
    
    boolean existsByGmail(String gmail);
}