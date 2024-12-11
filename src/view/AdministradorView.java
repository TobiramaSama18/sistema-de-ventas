package view;

import controller.SesionController;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import model.BaseDatos;
import model.Venta;
import view.ReporteVentasView;

public class AdministradorView extends JFrame {
    private JTable ventasTable;
    private JButton cerrarSesionButton;
    private JButton generarReporteButton;
    private DefaultTableModel tableModel;

    private BaseDatos baseDatos;
    private SesionController sesionController;

    // Constructor de la vista Administrador
    public AdministradorView(BaseDatos baseDatos, SesionController sesionController) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;

        // Configuración de la ventana
        setTitle("Sistema de Ventas - Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Etiqueta de título
        JLabel tituloLabel = new JLabel("Todas las Ventas");
        tituloLabel.setBounds(20, 20, 200, 25);
        add(tituloLabel);

        // Configuración de la tabla de ventas
        String[] columnNames = {"Producto", "Cantidad", "Precio Unitario", "Total", "Vendedor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ventasTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ventasTable);
        scrollPane.setBounds(20, 50, 540, 250);
        add(scrollPane);

        // Botón para generar reporte
        generarReporteButton = new JButton("Generar Reporte");
        generarReporteButton.setBounds(20, 320, 150, 25);
        add(generarReporteButton);

        generarReporteButton.addActionListener(e -> generarReporte());

        // Botón para cerrar sesión
        cerrarSesionButton = new JButton("Cerrar Sesión");
        cerrarSesionButton.setBounds(440, 320, 120, 25);
        add(cerrarSesionButton);

        cerrarSesionButton.addActionListener(e -> cerrarSesion());

        // Cargar datos iniciales
        cargarVentas();

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Mostrar la ventana
        setVisible(true);
    }

    // Cargar las ventas en la tabla
    public void cargarVentas() {
        tableModel.setRowCount(0); // Limpiar la tabla antes de cargar
        for (Venta venta : baseDatos.getVentas()) {
            // Llenar las filas con la información de las ventas
            Object[] row = {
                venta.getProducto(),
                venta.getCantidad(),
                venta.getPrecioUnitario(),
                venta.getTotal(),
                venta.getVendedor().getNombre()  // Obtener el nombre del vendedor
            };
            tableModel.addRow(row);
        }
    }

    // Acción para cerrar la sesión del administrador
    private void cerrarSesion() {
        sesionController.cerrarSesion();
        new LoginView(baseDatos, sesionController).setVisible(true);
        dispose();  // Cerrar la ventana actual
    }

    // Generar el reporte de ventas utilizando la clase ReporteVentas
    private void generarReporte() {
        ReporteVentasView reporteVentas = new ReporteVentasView(baseDatos);
        reporteVentas.generarReporte();  // Mostrar el reporte en pantalla
    }
}