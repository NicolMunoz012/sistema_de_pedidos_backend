package com.example.SistemaDePedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SistemaDePedidos.model.Categoria;
import com.example.SistemaDePedidos.model.Item;
import com.example.SistemaDePedidos.repository.ItemRepository;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;
    
    public Item crearItem(Item item) {
        return itemRepository.save(item);
    }
    
    public Item obtenerItem(String idItem) {
        return itemRepository.findById(idItem)
                .orElse(null);
    }
    
    public Item obtenerItemPorNombre(String nombre) {
        return itemRepository.findByNombre(nombre)
                .orElse(null);
    }
    
    public Item actualizarItem(String idItem, Item item) {
        Item itemExistente = obtenerItem(idItem);
        if (itemExistente != null) {
            item.setIdItem(idItem);
            return itemRepository.save(item);
        }
        return null;
    }
    
    public void eliminarItem(String idItem) {
        itemRepository.deleteById(idItem);
    }
    
    public List<Item> listarTodosLosItems() {
        return itemRepository.findAll();  
    }
    
    public List<Item> listarItemsPorCategoria(Categoria categoria) {
        return itemRepository.findByCategoria(categoria); 
    }
    
    public List<Item> listarItemsDisponibles() {
        return itemRepository.findByDisponibilidad(true); 
    }
    
    public void cambiarDisponibilidad(String idItem, boolean disponible) {
        Item item = obtenerItem(idItem);
        if (item != null) {
            item.setDisponibilidad(disponible);
            itemRepository.save(item);
        }
    }
    
    public boolean validarPrecio(double precio) {
        return precio > 0;
    }
    
    public List<Item> buscarItems(String criterio) {
        return itemRepository.findByNombreContainingIgnoreCase(criterio); 
    }
} 