package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.BaseDatos;
import controller.SesionController;
import model.Venta;
import model.Producto;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

public class ReporteVentasView extends JFrame {
    private JTable ventasTable;
    private JButton imprimirButton; // Botón para imprimir
    private JButton okButton; // Botón para regresar al sistema del administrador
    private DefaultTableModel tableModel;

    private BaseDatos baseDatos;
    private SesionController sesionController;
    private SistemaAdministradorView ventanaAdministrador; // Referencia a la ventana del administrador

    // Constructor para mostrar el reporte
    public ReporteVentasView(BaseDatos baseDatos, SesionController sesionController, DefaultTableModel tableModel, SistemaAdministradorView ventanaAdministrador) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;
        this.tableModel = tableModel;
        this.ventanaAdministrador = ventanaAdministrador; // Asignar la referencia de la ventana del administrador

        // Configuración de la ventana de reporte
        setTitle("Reporte de Ventas");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Etiqueta de título
        JLabel tituloLabel = new JLabel("Reporte de Ventas");
        tituloLabel.setBounds(20, 20, 200, 25);
        add(tituloLabel);

        // Configuración de la tabla de ventas
        ventasTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ventasTable);
        scrollPane.setBounds(20, 50, 540, 250);
        add(scrollPane);

        // Botón para imprimir
        imprimirButton = new JButton("Imprimir");
        imprimirButton.setBounds(200, 320, 120, 25);
        add(imprimirButton);
        imprimirButton.addActionListener(e -> imprimirVentas());

        // Botón "OK" para regresar al sistema del administrador
        okButton = new JButton("OK");
        okButton.setBounds(340, 320, 120, 25);
        add(okButton);
        okButton.addActionListener(e -> regresarAlSistemaAdministrador());

        // Centrar la ventana
        setLocationRelativeTo(null);

        // Mostrar la ventana
        setVisible(true);
    }

    // Método para imprimir la tabla de ventas
    private void imprimirVentas() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            try {
                ventasTable.print(); // Imprimir la tabla de ventas
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Error al intentar imprimir: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para regresar al sistema del administrador
    private void regresarAlSistemaAdministrador() {
        // Regresar al sistema del administrador sin abrir una nueva ventana
        // Simplemente ocultamos la ventana actual (reporte de ventas) y mostramos la ventana principal del administrador
        setVisible(false); // Ocultar la ventana de reporte de ventas
        ventanaAdministrador.setVisible(true); // Mostrar la ventana del administrador
        dispose(); // Liberar la memoria de la ventana de reporte
    }

    // Método para actualizar la tabla con las ventas más recientes
    public void actualizarTablaVentas() {
        // Limpiar la tabla antes de cargar los nuevos datos
        tableModel.setRowCount(0);

        // Formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Obtener las ventas más recientes y agregarlas a la tabla
        for (Venta venta : baseDatos.getVentas()) {
            String fechaVenta = venta.getFechaHora().format(formatter);  // Formatear la fecha

            for (Producto producto : venta.getProductos()) {
                // Agregar las filas correspondientes a la tabla
                Object[] row = {
                    venta.getVendedor().getNombre(),  // Vendedor
                    producto.getDescripcion(),        // Producto
                    producto.getCantidad(),           // Cantidad
                    producto.getPrecio(),             // Precio Unitario
                    fechaVenta,                       // Fecha
                    producto.getCantidad() * producto.getPrecio().doubleValue()  // Total
                };
                tableModel.addRow(row);
            }
        }
    }
}
