package view;

import controller.SesionController;
import model.BaseDatos;
import model.Venta;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteVentasView extends JFrame {

    private BaseDatos baseDatos;
    private SesionController sesionController;

    public ReporteVentasView(BaseDatos baseDatos, SesionController sesionController) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;

        setTitle("Reporte de Ventas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear panel para contener los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear un botón para generar el reporte
        JButton generarReporteButton = new JButton("Generar Reporte");
        generarReporteButton.addActionListener(e -> generarReporte());
        
        // Crear área de texto para mostrar el reporte
        JTextArea reporteTextArea = new JTextArea();
        reporteTextArea.setEditable(false);  // Hacer el JTextArea no editable
        reporteTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Crear un JScrollPane para permitir desplazamiento en el reporte
        JScrollPane scrollPane = new JScrollPane(reporteTextArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        // Agregar los componentes al panel
        panel.add(generarReporteButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Agregar el panel al JFrame
        add(panel);

        // Centrar la ventana
        setLocationRelativeTo(null);

        // Hacer visible la ventana
        setVisible(true);
    }

    ReporteVentasView(BaseDatos baseDatos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Método para generar el reporte de ventas
    void generarReporte() {
        StringBuilder reporte = new StringBuilder();
        
        // Obtener la fecha y hora actual para el reporte
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        
        // Escribir el encabezado del reporte
        reporte.append("Reporte de Ventas\n");
        reporte.append("Fecha de Generación: ").append(fecha).append("\n");
        reporte.append("-------------------------\n");
        reporte.append(String.format("%-20s %-10s %-20s %-10s\n", "Producto", "Cantidad", "Precio Unitario", "Total"));

        // Escribir los datos de las ventas
        double totalGeneral = 0;
        for (Venta venta : baseDatos.getVentas()) {
            reporte.append(String.format("%-20s %-10d %-20.2f %-10.2f\n",
                    venta.getProducto(), venta.getCantidad(), venta.getPrecioUnitario(), venta.getTotal()));
            totalGeneral += venta.getTotal();
        }

        // Escribir el total general
        reporte.append("-------------------------\n");
        reporte.append(String.format("TOTAL GENERAL: %.2f\n", totalGeneral));
        reporte.append("-------------------------\n");
        reporte.append("¡Gracias por usar el sistema!");

        // Actualizar el JTextArea con el reporte generado
        JTextArea reporteTextArea = new JTextArea(reporte.toString());
        reporteTextArea.setEditable(false);  // Hacer el JTextArea no editable
        reporteTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Crear un JScrollPane para permitir desplazamiento en el reporte
        JScrollPane scrollPane = new JScrollPane(reporteTextArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        // Mostrar el reporte en un cuadro de diálogo
        JOptionPane.showMessageDialog(this, scrollPane, "Reporte de Ventas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Inicializar la base de datos y el controlador de sesión, luego mostrar la vista
        SwingUtilities.invokeLater(() -> {
            BaseDatos baseDatos = new BaseDatos();  // Suponiendo que BaseDatos tiene un constructor por defecto
            SesionController sesionController = new SesionController(baseDatos);
            new ReporteVentasView(baseDatos, sesionController);
        });
    }
}