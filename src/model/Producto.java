package model;

public class Producto {
    private String codigo;       // Código del producto
    private String descripcion;  // Descripción del producto
    private double precio;       // Precio del producto
    private int cantidad;        // Cantidad disponible en stock

    // Constructor
    public Producto(String codigo, String descripcion, double precio, int cantidad) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    Producto(String productoDescripcion, double precioUnitario, int cantidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Getters y setters
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Método para reducir el stock
    public boolean reducirStock(int cantidad) {
        if (cantidad <= this.cantidad) {
            this.cantidad -= cantidad;
            return true;  // Stock reducido correctamente
        }
        return false;  // No hay suficiente stock
    }

    // Método para aumentar el stock
    public void aumentarStock(int cantidad) {
        this.cantidad += cantidad;
    }

    // Método para obtener el valor total de un producto en base al precio y la cantidad
    public double obtenerValorTotal(int cantidad) {
        return this.precio * cantidad;
    }
}