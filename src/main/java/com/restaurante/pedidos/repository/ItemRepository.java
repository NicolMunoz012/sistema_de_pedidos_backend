package com.restaurante.pedidos.repository;

import com.restaurante.pedidos.model.Categoria;
import com.restaurante.pedidos.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Item guardar(Item item);
    Item buscarPorNombre(String nombre);
    List<Item> buscarPorCategoria(Categoria categoria);
    List<Item> buscarDisponibles();
    List<Item> buscarTodos();
    Item actualizar(Item item);
    void eliminar(String nombre);
    boolean existePorNombre(String nombre);
}
