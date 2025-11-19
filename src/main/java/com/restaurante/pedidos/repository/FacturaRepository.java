package com.restaurante.pedidos.repository;

import com.restaurante.pedidos.model.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface FacturaRepository extends MongoRepository<Factura, Integer> {
    Factura guardar(Factura factura);
    Factura buscarPorCodigo(int codigoFactura);
    List<Factura> buscarPorCliente(String idCliente);
    List<Factura> buscarPorRangoFechas(Date fechaInicio, Date fechaFin);
    List<Factura> buscarTodas();
    Factura actualizar(Factura factura);
    void eliminar(int codigoFactura);
    boolean existePorCodigo(int codigoFactura);
    int generarCodigoFactura();
}
