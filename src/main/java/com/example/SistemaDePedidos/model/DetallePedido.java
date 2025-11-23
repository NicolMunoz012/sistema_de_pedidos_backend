package com.example.SistemaDePedidos.model;

/**
 * DetallePedido representa una línea del pedido (composición fuerte).
 * NO puede existir sin su Pedido padre.
 * Multiplicidad: DetallePedido (1) -> Item (1)
 * Un detalle contiene UN solo item con su cantidad.
 * Ejemplo: "2 hamburguesas clásicas", "1 limonada", "3 papas grandes"
 */
public class DetallePedido {
    private String idDetalle;
    private Item item; // ✅ UN solo item por detalle (no es List<Item>)
    private int cantidad;
    private double precioUnitario; // ✅ Precio congelado al momento del pedido
    private double subtotal; // ✅ Calculado: precioUnitario * cantidad
    private String observaciones;

    public DetallePedido() {
    }

    /**
     * Constructor que congela el precio del item al momento de crear el detalle
     */
    public DetallePedido(String idDetalle, Item item, int cantidad, String observaciones) {
        this.idDetalle = idDetalle;
        this.item = item;
        this.cantidad = cantidad;
        // ✅ Congelar precio unitario del item en este momento
        this.precioUnitario = item != null ? item.getPrecio() : 0.0;
        // ✅ Calcular subtotal automáticamente
        this.subtotal = this.precioUnitario * cantidad;
        this.observaciones = observaciones;
    }

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        // ✅ Recalcular subtotal cuando cambia la cantidad
        this.subtotal = this.precioUnitario * cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * El precio unitario NO debe cambiar después de creado el detalle.
     * Representa el precio histórico del item al momento del pedido.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        // ✅ Recalcular subtotal cuando cambia el precio
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    /**
     * El subtotal se calcula automáticamente.
     * No debería ser modificado directamente.
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    /**
     * Método auxiliar para recalcular el subtotal
     */
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "idDetalle='" + idDetalle + '\'' +
                ", item=" + (item != null ? item.getNombre() : "null") +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
