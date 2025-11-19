package com.restaurante.pedidos.service;

import com.restaurante.pedidos.model.Categoria;
import com.restaurante.pedidos.model.Item;
import com.restaurante.pedidos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item crearItem(Item item) {
        if (validarPrecio(item.getPrecio())) {
            return itemRepository.save(item);
        }
        return null;
    }

    public Item obtenerItem(String nombre) {
        return itemRepository.findById(nombre).orElse(null);
    }

    public Item actualizarItem(Item item) {
        return itemRepository.save(item);
    }

    public void eliminarItem(String nombre) {
        itemRepository.deleteById(nombre);
    }

    public List<Item> listarTodosLosItems() {
        return itemRepository.findAll();
    }

    public List<Item> listarItemsPorCategoria(Categoria categoria) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<Item> listarItemsDisponibles() {
        return itemRepository.findAll().stream()
                .filter(Item::isDisponibilidad)
                .collect(Collectors.toList());
    }

    public void cambiarDisponibilidad(String nombre, boolean disponible) {
        Item item = obtenerItem(nombre);
        if (item != null) {
            item.setDisponibilidad(disponible);
            itemRepository.save(item);
        }
    }

    public boolean validarPrecio(double precio) {
        return precio > 0;
    }

    public List<Item> buscarItems(String criterio) {
        return itemRepository.findAll().stream()
                .filter(item -> item.getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                        item.getDescripcion().toLowerCase().contains(criterio.toLowerCase()))
                .collect(Collectors.toList());
    }
}
