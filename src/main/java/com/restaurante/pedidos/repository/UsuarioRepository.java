package com.restaurante.pedidos.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.pedidos.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorId(String idUsuario);
    Usuario buscarPorGmail(String gmail);
    List<Usuario> buscarTodos();
    Usuario actualizar(Usuario usuario);
    void eliminar(String idUsuario);
    boolean existePorGmail(String gmail);
    Usuario findByGmail(String gmail);
}
