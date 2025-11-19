package com.example.SistemaDePedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SistemaDePedidos.model.Categoria;
import com.example.SistemaDePedidos.model.Item;
import com.example.SistemaDePedidos.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> crearItem(@RequestBody Item item) {
        Item newItem = itemService.crearItem(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping("/{idItem}")
    public Item obtenerItem(@PathVariable String idItem) {
        return itemService.obtenerItem(idItem);
    }

    @GetMapping
    public List<Item> listarTodosLosItems() {
        return itemService.listarTodosLosItems();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Item> listarItemsPorCategoria(@PathVariable Categoria categoria) {
        return itemService.listarItemsPorCategoria(categoria);
    }

    @PutMapping("/{idItem}")
    public Item actualizarItem(@PathVariable String idItem, @RequestBody Item item) {
        return itemService.actualizarItem(idItem, item);
    }

    @DeleteMapping("/{idItem}")
    public void eliminarItem(@PathVariable String idItem) {
        itemService.eliminarItem(idItem);
    }

    @PutMapping("/{idItem}/disponibilidad")
    public void cambiarDisponibilidad(@PathVariable String idItem, @RequestParam boolean disponible) {
        itemService.cambiarDisponibilidad(idItem, disponible);
    }

    @GetMapping("/buscar")
    public List<Item> buscarItemsPorNombre(@RequestParam String textoBusqueda) {
        return itemService.buscarItems(textoBusqueda);
    }
}
