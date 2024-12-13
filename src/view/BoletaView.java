package view;

import model.Venta;

import javax.print.*;
import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import java.util.ArrayList;

public class BoletaView extends JFrame {

    private ArrayList<Venta> ventas;
    private JTextArea boletaTextArea;

    public BoletaView(ArrayList<Venta> ventas) {
        this.ventas = ventas;

        setTitle("Boleta de Venta");
        setSize(500, 600);
        setLayout(new BorderLayout());

        boletaTextArea = new JTextArea();
        boletaTextArea.setEditable(false);
        boletaTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));  // Fuente monoespaciada para alineación
        boletaTextArea.setText(generarBoleta());  // Generar boleta al inicializar

        JScrollPane scrollPane = new JScrollPane(boletaTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton imprimirButton = new JButton("Imprimir Boleta");
        imprimirButton.addActionListener(e -> imprimirBoleta());
        add(imprimirButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private String generarBoleta() {
        StringBuilder boleta = new StringBuilder();
        boleta.append("==================================================================\n");
        boleta.append("                            Boleta de Venta            \n");
        boleta.append("==================================================================\n");

        double totalGeneral = 0;
        boleta.append(String.format("%-20s %-10s %-15s %-15s\n",    "Producto", "Cantidad", "Precio Unit.", "Total"));
        boleta.append("------------------------------------------------------------------\n");

        for (Venta venta : ventas) {
            boleta.append(String.format("%-20s %-10d %-15.2f %-15.2f\n", 
                    venta.getProducto().getNombre(), 
                    venta.getCantidad(), 
                    venta.getPrecioUnitario(), 
                    venta.getTotal()));
            totalGeneral += venta.getTotal().doubleValue();
        }

        boleta.append("------------------------------------------------------------------\n");
        boleta.append(String.format("%-20s %-10s %-15s %-15.2f\n", "", "", "TOTAL GENERAL", totalGeneral));
        boleta.append("\n==================================================================\n");
        boleta.append("                     ¡Gracias por su compra!     \n");
        boleta.append("==================================================================\n");

        return boleta.toString();
    }

    private void imprimirBoleta() {
        try {
            // Crear un PrinterJob
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;  // Solo una página
                }

                // Dibujar el contenido de la boleta en la página
                graphics.setFont(new Font("Monospaced", Font.PLAIN, 12));
                int x = 50;  // Márgenes
                int y = 50;

                String boletaTexto = boletaTextArea.getText();
                String[] lines = boletaTexto.split("\n");

                for (String line : lines) {
                    graphics.drawString(line, x, y);
                    y += 15;  // Espacio entre líneas
                }

                return Printable.PAGE_EXISTS;
            });

            // Mostrar el cuadro de diálogo de impresión
            if (printerJob.printDialog()) {
                printerJob.print();  // Imprimir si el usuario acepta
                JOptionPane.showMessageDialog(this, "Boleta enviada a la impresora.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Error al imprimir la boleta: " + ex.getMessage(), "Error de impresión", JOptionPane.ERROR_MESSAGE);
        }
    }
}
