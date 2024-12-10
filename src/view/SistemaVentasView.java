package view;

import controller.SesionController;
import model.BaseDatos;
import model.Venta;
import model.Vendedor;
import model.Venta.FormaDePago;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SistemaVentasView extends JFrame {

    private BaseDatos baseDatos;
    private SesionController sesionController;
    private Vendedor vendedor;

    private JTextField productoTextField;
    private JTextField cantidadTextField;
    private JTextField precioUnitarioTextField;
    private JTextField totalField;

    private ArrayList<Venta> ventas; // Lista de ventas registradas temporalmente
    private JTable ventasTable; // Tabla de ventas

    // Constructor con parámetros
    public SistemaVentasView(BaseDatos baseDatos, SesionController sesionController, Vendedor vendedor) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;
        this.vendedor = vendedor;
        this.ventas = new ArrayList<>();

        setTitle("Sistema de Ventas - Vendedor: " + vendedor.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Título de la ventana
        JLabel tituloLabel = new JLabel("SISTEMA DE VENTAS");
        tituloLabel.setFont(new Font("Serif", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBounds(200, 10, 400, 30);
        add(tituloLabel);

        // Panel de Datos Generales (productos, cantidad, precio)
        JPanel datosPanel = new JPanel();
        datosPanel.setBorder(BorderFactory.createTitledBorder("DATOS GENERALES"));
        datosPanel.setLayout(null);
        datosPanel.setBounds(20, 60, 350, 230);
        add(datosPanel);

        // Producto
        JLabel productoLabel = new JLabel("Producto:");
        productoLabel.setBounds(10, 30, 80, 25);
        datosPanel.add(productoLabel);
        productoTextField = new JTextField();
        productoTextField.setBounds(100, 30, 200, 25);
        datosPanel.add(productoTextField);

        // Cantidad
        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 70, 80, 25);
        datosPanel.add(cantidadLabel);
        cantidadTextField = new JTextField();
        cantidadTextField.setBounds(100, 70, 200, 25);
        datosPanel.add(cantidadTextField);

        // Precio Unitario
        JLabel precioUnitarioLabel = new JLabel("Precio:");
        precioUnitarioLabel.setBounds(10, 110, 80, 25);
        datosPanel.add(precioUnitarioLabel);
        precioUnitarioTextField = new JTextField();
        precioUnitarioTextField.setBounds(100, 110, 200, 25);
        datosPanel.add(precioUnitarioTextField);

        // Total
        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setBounds(10, 150, 80, 25);
        datosPanel.add(totalLabel);
        totalField = new JTextField();
        totalField.setBounds(100, 150, 200, 25);
        datosPanel.add(totalField);
        totalField.setEditable(false); // Total es calculado

        // Botón para registrar la venta
        JButton registrarVentaButton = new JButton("Registrar Venta");
        registrarVentaButton.setBounds(10, 190, 150, 30);
        datosPanel.add(registrarVentaButton);
        registrarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarVenta();
            }
        });

        // Botón para guardar las ventas
        JButton guardarVentaButton = new JButton("Guardar Venta");
        guardarVentaButton.setBounds(190, 190, 150, 30);
        datosPanel.add(guardarVentaButton);
        guardarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarVenta();
            }
        });

        // Panel de Forma de Pago
        JPanel formaPagoPanel = new JPanel();
        formaPagoPanel.setBorder(BorderFactory.createTitledBorder("FORMA DE PAGO"));
        formaPagoPanel.setLayout(new GridLayout(1, 3, 10, 10));
        formaPagoPanel.setBounds(400, 60, 350, 100);
        add(formaPagoPanel);

        // Botones de forma de pago
        JButton efectivoButton = new JButton("Efectivo");
        formaPagoPanel.add(efectivoButton);
        efectivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarEfectivo();
            }
        });

        JButton tarjetaButton = new JButton("Tarjeta");
        formaPagoPanel.add(tarjetaButton);
        tarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarTarjeta();
            }
        });

        JButton mixtoButton = new JButton("Mixto");
        formaPagoPanel.add(mixtoButton);
        mixtoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarMixto();
            }
        });

        // Botón para cerrar sesión
        JButton cerrarSesionButton = new JButton("Cerrar Sesión");
        cerrarSesionButton.setBounds(650, 500, 120, 30);
        add(cerrarSesionButton);
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Tabla de ventas
        ventasTable = new JTable(new Object[][] {}, new String[] {"Producto", "Cantidad", "Precio Unitario", "Total"});
        JScrollPane tablaScrollPane = new JScrollPane(ventasTable);
        tablaScrollPane.setBounds(20, 300, 730, 150);
        add(tablaScrollPane);

        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void registrarVenta() {
        try {
            String producto = productoTextField.getText();
            int cantidad = Integer.parseInt(cantidadTextField.getText());
            double precioUnitario = Double.parseDouble(precioUnitarioTextField.getText());

            // Crear una venta temporal y agregarla a la lista de ventas
            FormaDePago formaDePago = FormaDePago.efectivo; // Asume que la forma de pago es 'efectivo' por defecto
            Venta ventaTemporal = new Venta(producto, cantidad, precioUnitario, vendedor, formaDePago);
            ventas.add(ventaTemporal);

            // Actualizar el total de la venta
            totalField.setText(String.valueOf(ventaTemporal.getTotal()));

            // Limpiar los campos de entrada
            productoTextField.setText("");
            cantidadTextField.setText("");
            precioUnitarioTextField.setText("");

            // Actualizar la tabla de ventas
            actualizarTabla();

            JOptionPane.showMessageDialog(this, "Venta registrada.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        String[][] data = new String[ventas.size()][4];
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            data[i][0] = venta.getProducto();
            data[i][1] = String.valueOf(venta.getCantidad());
            data[i][2] = String.valueOf(venta.getPrecioUnitario());
            data[i][3] = String.valueOf(venta.getTotal());
        }

        ventasTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"Producto", "Cantidad", "Precio Unitario", "Total"}));
    }

    private void guardarVenta() {
        if (!ventas.isEmpty()) {
            for (Venta venta : ventas) {
                baseDatos.agregarVenta(venta); // Guardar en la base de datos
            }

            String boleta = generarBoleta();
            JOptionPane.showMessageDialog(this, boleta, "Boleta de Venta", JOptionPane.INFORMATION_MESSAGE);

            ventas.clear();
            totalField.setText(""); // Limpiar total
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ventas registradas para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String generarBoleta() {
        StringBuilder boleta = new StringBuilder("BOLETA DE VENTA\n");
        boleta.append("Vendedor: ").append(vendedor.getNombre()).append("\n\n");
        double totalGeneral = 0;

        for (Venta venta : ventas) {
            boleta.append("Producto: ").append(venta.getProducto()).append("\n")
                  .append("Cantidad: ").append(venta.getCantidad()).append("\n")
                  .append("Precio Unitario: ").append(venta.getPrecioUnitario()).append("\n")
                  .append("Total: ").append(venta.getTotal()).append("\n\n");
            totalGeneral += venta.getTotal();
        }

        boleta.append("TOTAL GENERAL: ").append(totalGeneral).append("\n");
        return boleta.toString();
    }

    private void pagarEfectivo() {
        try {
            double total = Double.parseDouble(totalField.getText());
            double efectivo = Double.parseDouble(JOptionPane.showInputDialog(this, "Ingrese el monto en efectivo:"));

            if (efectivo >= total) {
                double cambio = efectivo - total;
                JOptionPane.showMessageDialog(this, "Pago realizado con éxito. Cambio: " + cambio);
            } else {
                JOptionPane.showMessageDialog(this, "El efectivo ingresado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarTarjeta() {
        try {
            double total = Double.parseDouble(totalField.getText());
            JOptionPane.showMessageDialog(this, "Pago realizado con tarjeta. Total: " + total);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarMixto() {
        try {
            double total = Double.parseDouble(totalField.getText());
            double efectivo = Double.parseDouble(JOptionPane.showInputDialog(this, "Ingrese el monto en efectivo:"));
            double tarjeta = total - efectivo;

            if (efectivo >= 0) {
                JOptionPane.showMessageDialog(this, "Pago mixto realizado con éxito. Efectivo: " + efectivo + " y Tarjeta: " + tarjeta);
            } else {
                JOptionPane.showMessageDialog(this, "El efectivo ingresado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cerrarSesion() {
        // Aquí iría el código para cerrar la sesión y volver a la pantalla de inicio de sesión
        System.exit(0);
    }
}