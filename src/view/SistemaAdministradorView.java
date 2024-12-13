package view;

import controller.SesionController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.BaseDatos;
import model.Venta;
import model.Producto;
import model.Administrador; // Importa la clase Administrador
import java.time.format.DateTimeFormatter;

public class SistemaAdministradorView extends JFrame {
    private JTable ventasTable;
    private JButton cerrarSesionButton;
    private JButton generarReporteButton;
    private DefaultTableModel tableModel;

    private BaseDatos baseDatos;
    private SesionController sesionController;
    private Administrador administradorActual; // Cambio: Usamos Administrador en lugar de Usuario

    // Constructor modificado con Administrador
    public SistemaAdministradorView(BaseDatos baseDatos, SesionController sesionController, Administrador administrador) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;
        this.administradorActual = administrador; // Usamos Administrador en lugar de Usuario

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
        String[] columnNames = {"Vendedor", "Producto", "Cantidad", "Precio Unitario", "Fecha", "Total"};
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formato de fecha
        for (Venta venta : baseDatos.getVentas()) {
            String fechaVenta = venta.getFechaHora().format(formatter); // Formatear la fecha y hora usando DateTimeFormatter
            for (Producto producto : venta.getProductos()) {
                // Llenar las filas con la información de las ventas en el nuevo orden
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

    // Acción para generar el reporte de ventas
    private void generarReporte() {
        // Crear la vista del reporte y mostrarla, pasando 'this' (ventana actual) como cuarto argumento
        new ReporteVentasView(baseDatos, sesionController, tableModel, this); // Pasa el modelo de la tabla y la ventana del administrador

        // No se inhabilita el botón, se deja habilitado para generar más reportes si es necesario
    }

    // Acción para cerrar la sesión del administrador
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra la ventana actual (de ventas)

            // Crear y mostrar la ventana de login
            LoginView loginView = new LoginView(baseDatos, sesionController); // Suponiendo que LoginView requiere estos parámetros
            loginView.setVisible(true);
        }
    }
}
