package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Producto {
    private String codigo;       // Código del producto
    private String descripcion;  // Descripción del producto (usada como nombre en este caso)
    private BigDecimal precio;   // Precio del producto
    private int cantidad;        // Cantidad disponible en stock

    // Constructor
    public Producto(String codigo, String descripcion, BigDecimal precio, int cantidad) {
        this.codigo = Objects.requireNonNull(codigo, "El código no puede ser nulo.");
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser vacío.");
        }

        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula.");
        if (descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser vacía.");
        }

        this.precio = Objects.requireNonNull(precio, "El precio no puede ser nulo.");
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    // Método para obtener el nombre del producto (se asume que la descripción actúa como nombre)
    public String getNombre() {
        return descripcion;
    }

    // Métodos adicionales
    public boolean reducirStock(int cantidad) {
        if (cantidad > this.cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock para realizar la venta.");
        }
        this.cantidad -= cantidad;
        return true;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a aumentar no puede ser negativa.");
        }
        this.cantidad += cantidad;
    }

    public BigDecimal obtenerValorTotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    @Override
    public String toString() {
        return String.format("Producto [codigo=%s, descripcion=%s, precio=%s, cantidad=%d]", 
                             codigo, descripcion, precio, cantidad);
    }
}
