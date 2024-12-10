package model;

import model.Producto;
import model.Vendedor;
import model.Venta.FormaDePago;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private String codigo;
    private List<Producto> productos;
    private Vendedor vendedor;
    private double total;
    private LocalDateTime fechaHora;
    private FormaDePago formaDePago;

    // Constructor principal
    public Venta(String codigo, List<Producto> productos, Vendedor vendedor, LocalDateTime fechaHora, FormaDePago formaDePago) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        }
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos no puede estar vacía.");
        }
        if (vendedor == null) {
            throw new IllegalArgumentException("El vendedor no puede ser nulo.");
        }
        if (formaDePago == null) {
            throw new IllegalArgumentException("La forma de pago no puede ser nula.");
        }

        this.codigo = codigo;
        this.productos = productos;
        this.vendedor = vendedor;
        this.fechaHora = fechaHora;
        this.formaDePago = formaDePago;
        this.total = calcularTotal();  // Calcular el total al crear la venta
    }

    // Constructor alternativo para una venta con un solo producto
    public Venta(String productoDescripcion, int cantidad, double precioUnitario, Vendedor vendedor, FormaDePago formaDePago) {
        this.codigo = generarCodigo();  // Generar un código único para la venta
        this.productos = List.of(new Producto(productoDescripcion, precioUnitario, cantidad));  // Crear el producto
        this.vendedor = vendedor;
        this.fechaHora = LocalDateTime.now(); // Fecha y hora actual
        this.formaDePago = formaDePago;
        this.total = calcularTotal();  // Calcular el total
    }

    // Método para calcular el total de la venta sumando el total de todos los productos
    private double calcularTotal() {
        return productos.stream()
                        .mapToDouble(p -> p.getPrecio() * p.getCantidad())  // Precio * cantidad de cada producto
                        .sum();  // Sumar todos los totales
    }

    // Método para generar un código único para cada venta
    private String generarCodigo() {
        return "V" + System.currentTimeMillis(); // Genera un código único basado en el tiempo
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        this.total = calcularTotal();  // Actualiza el total cuando cambian los productos
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(FormaDePago formaDePago) {
        if (formaDePago == null) {
            throw new IllegalArgumentException("La forma de pago no puede ser nula.");
        }
        this.formaDePago = formaDePago;
    }

    // Método para generar un resumen de la venta
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Venta Código: ").append(codigo).append("\n")
               .append("Vendedor: ").append(vendedor.getNombre()).append("\n")
               .append("Fecha y Hora: ").append(fechaHora).append("\n")
               .append("Forma de Pago: ").append(formaDePago).append("\n")
               .append("Productos:\n");

        for (Producto producto : productos) {
            resumen.append("- ").append(producto.getDescripcion())
                   .append(" (Cantidad: ").append(producto.getCantidad())
                   .append(", Precio Unitario: ").append(producto.getPrecio())
                   .append(", Total: ").append(producto.getCantidad() * producto.getPrecio()).append(")\n");
        }

        resumen.append("TOTAL: ").append(total).append("\n");  // Mostrar total de la venta

        return resumen.toString();  // Retornar el resumen completo de la venta
    }

    public String getProducto() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public String getCantidad() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public String getPrecioUnitario() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    // Clase interna para manejar las formas de pago
    public static class FormaDePago {

        public static FormaDePago efectivo;
        private String metodo;

        public FormaDePago(String metodo) {
            this.metodo = metodo;
        }

        @Override
        public String toString() {
            return metodo;  // Retorna el nombre del método de pago
        }
    }
}
