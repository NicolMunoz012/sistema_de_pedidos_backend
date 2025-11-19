package com.example.SistemaDePedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Item crearItem(@RequestBody Item item) {
        return itemService.crearItem(item);
    }

    @GetMapping("/{nombre}")
    public Item obtenerItem(@PathVariable String nombre) {
        return itemService.obtenerItem(nombre);
    }

    @GetMapping
    public List<Item> listarTodosLosItems() {
        return itemService.listarTodosLosItems();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Item> listarItemsPorCategoria(@PathVariable Categoria categoria) {
        return itemService.listarItemsPorCategoria(categoria);
    }

    @PutMapping("/{nombre}")
    public Item actualizarItem(@PathVariable String nombre, @RequestBody Item item) {
        item.setNombre(nombre);
        return itemService.actualizarItem(item);
    }

    @DeleteMapping("/{nombre}")
    public void eliminarItem(@PathVariable String nombre) {
        itemService.eliminarItem(nombre);
    }

    @PutMapping("/{nombre}/disponibilidad")
    public void cambiarDisponibilidad(@PathVariable String nombre, @RequestParam boolean disponible) {
        itemService.cambiarDisponibilidad(nombre, disponible);
    }

    @GetMapping("/buscar")
    public List<Item> buscarItemsPorNombre(@RequestParam String textoBusqueda) {
        return itemService.buscarItems(textoBusqueda);
    }
}
