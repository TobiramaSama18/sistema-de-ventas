package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venta {
    private String codigo;
    private List<Producto> productos;
    private Vendedor vendedor;
    private BigDecimal total;
    private LocalDateTime fechaHora;
    private FormaDePago formaDePago;

    // Constructor principal
    public Venta(String codigo, List<Producto> productos, Vendedor vendedor, LocalDateTime fechaHora, FormaDePago formaDePago) {
        this.codigo = Objects.requireNonNull(codigo, "El código no puede ser nulo o vacío.");
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser vacío.");
        }
        this.productos = Objects.requireNonNull(productos, "La lista de productos no puede ser nula.");
        if (productos.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos no puede estar vacía.");
        }
        this.vendedor = Objects.requireNonNull(vendedor, "El vendedor no puede ser nulo.");
        this.formaDePago = Objects.requireNonNull(formaDePago, "La forma de pago no puede ser nula.");
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha y hora no puede ser nula.");

        this.total = calcularTotal();
    }

    // Constructor adicional para un solo producto
    public Venta(String codigo, Producto producto, int cantidad, BigDecimal precioUnitario, Vendedor vendedor, FormaDePago formaDePago) {
        this.codigo = Objects.requireNonNull(codigo, "El código no puede ser nulo o vacío.");
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser vacío.");
        }
        Objects.requireNonNull(producto, "El producto no puede ser nulo.");
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0.");
        }
        this.productos = new ArrayList<>();
        producto.setCantidad(cantidad);
        producto.setPrecio(precioUnitario);
        this.productos.add(producto);

        this.vendedor = Objects.requireNonNull(vendedor, "El vendedor no puede ser nulo.");
        this.formaDePago = Objects.requireNonNull(formaDePago, "La forma de pago no puede ser nula.");
        this.fechaHora = LocalDateTime.now();

        this.total = calcularTotal();
    }

    // Método para calcular el total
    private BigDecimal calcularTotal() {
        return productos.stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Métodos getters
    public BigDecimal getTotal() {
        return total;
    }

    public String getCodigo() {
        return codigo;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public FormaDePago getFormaDePago() {
        return formaDePago;
    }

    // Método para obtener la fecha en formato string
    public String getFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHora.format(formatter);
    }

    // Métodos relacionados con productos
    public Producto getProducto(int index) {
        if (index < 0 || index >= productos.size()) {
            throw new IndexOutOfBoundsException("El índice está fuera de los límites.");
        }
        return productos.get(index);
    }

    public Producto getProducto() {
        if (productos.isEmpty()) {
            throw new IllegalStateException("No hay productos en la venta.");
        }
        return productos.get(0);
    }

    public int getCantidad() {
        return getProducto().getCantidad();
    }

    public BigDecimal getPrecioUnitario() {
        return getProducto().getPrecio();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setFormaDePago(FormaDePago formaDePago) {
        this.formaDePago = Objects.requireNonNull(formaDePago, "La forma de pago no puede ser nula.");
    }

    // Generar un resumen de la venta
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Venta Código: ").append(codigo).append("\n")
               .append("Vendedor: ").append(vendedor.getNombre()).append("\n")
               .append("Fecha y Hora: ").append(getFechaHoraFormateada()).append("\n") // Formateado
               .append("Forma de Pago: ").append(formaDePago).append("\n")
               .append("Productos:\n");

        for (Producto producto : productos) {
            resumen.append("- ").append(producto.getDescripcion())
                   .append(" (Cantidad: ").append(producto.getCantidad())
                   .append(", Precio Unitario: ").append(producto.getPrecio())
                   .append(", Total: ").append(producto.getPrecio().multiply(BigDecimal.valueOf(producto.getCantidad()))).append(")\n");
        }

        resumen.append("TOTAL: ").append(total.setScale(2, BigDecimal.ROUND_HALF_UP)).append("\n");
        return resumen.toString();
    }
}
