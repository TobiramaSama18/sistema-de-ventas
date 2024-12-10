package view;

import model.Venta;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

public class BoletaView extends JFrame implements Printable {
    private Venta venta;
    private JTextArea boletaTextArea;

    // Constructor para inicializar la vista de la boleta
    public BoletaView(Venta venta) {
        this.venta = venta;

        // Configuración de la ventana
        setTitle("Boleta de Venta");
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Crear un área de texto no editable para mostrar la boleta
        boletaTextArea = new JTextArea();
        boletaTextArea.setEditable(false);
        boletaTextArea.setText(generarBoleta());  // Generar boleta al inicializar

        // Añadir el área de texto en un JScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(boletaTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Crear botón para imprimir la boleta
        JButton imprimirButton = new JButton("Imprimir Boleta");
        imprimirButton.addActionListener(e -> imprimirBoleta());
        add(imprimirButton, BorderLayout.SOUTH);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Método para generar el texto de la boleta
    private String generarBoleta() {
        String boleta = "Boleta de Venta\n";
        boleta += "---------------------------\n";
        boleta += "Producto: " + venta.getProducto() + "\n";
        boleta += "Cantidad: " + venta.getCantidad() + "\n";
        boleta += "Precio Unitario: $" + venta.getPrecioUnitario() + "\n";
        boleta += "Total: $" + venta.getTotal() + "\n";
        boleta += "---------------------------\n";
        boleta += "¡Gracias por su compra!";
        return boleta;
    }

    // Método para manejar la impresión de la boleta
    private void imprimirBoleta() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);  // Configura la clase actual como el objeto imprimible

        if (printerJob.printDialog()) {  // Mostrar el cuadro de diálogo para la impresora
            try {
                printerJob.print();  // Intentar imprimir
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Error al imprimir la boleta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // Implementación del método print de Printable para imprimir la boleta
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return NO_SUCH_PAGE;  // Si ya se ha imprimido una página, no hay más páginas
        }

        // Ajustar la posición del gráfico según la página
        graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        boletaTextArea.printAll(graphics);  // Imprimir todo el contenido del área de texto
        return PAGE_EXISTS;  // Indicar que la página existe
    }
}