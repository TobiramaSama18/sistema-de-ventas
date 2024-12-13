package view;

import controller.SesionController;
import model.BaseDatos;
import model.Venta;
import model.Vendedor;
import model.FormaDePago;
import model.Producto;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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

        // Configuración de la ventana
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
        JButton guardarVentaButton = new JButton("Guardar y Imprimir");
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
        cerrarSesionButton.setBounds(650, 500, 120, 30); // Este botón se mantiene en su posición original
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

        // Botón para mostrar la boleta generada
        JButton mostrarBoletaButton = new JButton("Mostrar Boleta");
        mostrarBoletaButton.setBounds(400, 400, 150, 30);
        add(mostrarBoletaButton);
        mostrarBoletaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarBoleta();
            }
        });

        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void registrarVenta() {
        try {
            String productoNombre = productoTextField.getText();
            int cantidad = Integer.parseInt(cantidadTextField.getText());
            double precioUnitario = Double.parseDouble(precioUnitarioTextField.getText());

            // Crear objeto Producto y Venta
            Producto producto = new Producto(productoNombre, productoNombre, BigDecimal.valueOf(precioUnitario), cantidad);
            FormaDePago formaDePago = FormaDePago.EFECTIVO; // Forma de pago predeterminada (se actualizará después)
            Venta ventaTemporal = new Venta("COD123", producto, cantidad, BigDecimal.valueOf(precioUnitario), vendedor, formaDePago);
            ventas.add(ventaTemporal);

            // Actualizar el total de la venta
            actualizarTotalGeneral();

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

    private void actualizarTotalGeneral() {
        // Calcular el total general de todas las ventas
        double totalGeneral = 0;
        for (Venta venta : ventas) {
            totalGeneral += venta.getTotal().doubleValue();
        }

        // Actualizar el campo de texto con el total general
        totalField.setText(String.valueOf(totalGeneral));
    }

    private void actualizarTabla() {
        String[][] data = new String[ventas.size()][4];
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            data[i][0] = venta.getProducto().getNombre();
            data[i][1] = String.valueOf(venta.getCantidad());
            data[i][2] = String.valueOf(venta.getPrecioUnitario());
            data[i][3] = String.valueOf(venta.getTotal());
        }

        ventasTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"Producto", "Cantidad", "Precio Unitario", "Total"}));
    }

    private void mostrarBoleta() {
        // Crear y mostrar la boleta en una nueva ventana
        BoletaView boletaView = new BoletaView(ventas);
        boletaView.setVisible(true);
    }

    private void guardarVenta() {
        if (!ventas.isEmpty()) {
            // Verificamos si las ventas han sido pagadas antes de guardarlas
            boolean ventasPagadas = true;
            for (Venta venta : ventas) {
                if (venta.getFormaDePago() == null) {
                    ventasPagadas = false;
                    break;
                }
            }

            if (ventasPagadas) {
                // Guardar las ventas en la base de datos
                for (Venta venta : ventas) {
                    baseDatos.agregarVenta(venta); // Guardar en la base de datos
                }

                // Preguntar al usuario si desea ver la boleta
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Deseas ver la boleta de la venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    // Crear y mostrar la boleta si el usuario acepta
                    mostrarBoleta();
                }

                // Limpiar las ventas, total y actualizar la tabla
                ventas.clear();
                totalField.setText(""); // Limpiar total
                actualizarTabla();

                JOptionPane.showMessageDialog(this, "Ventas guardadas correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Debe realizar el pago antes de guardar la venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay ventas registradas para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void pagarEfectivo() {
        try {
            // Obtener el total general
            BigDecimal total = BigDecimal.valueOf(Double.parseDouble(totalField.getText()));
            double efectivo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto entregado por el cliente:"));
            if (efectivo < total.doubleValue()) {
                JOptionPane.showMessageDialog(this, "El monto entregado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal cambio = BigDecimal.valueOf(efectivo - total.doubleValue());
            JOptionPane.showMessageDialog(this, "Pago realizado con éxito. El cambio es: " + cambio);

            // Actualizar la forma de pago para las ventas
            for (Venta venta : ventas) {
                venta.setFormaDePago(FormaDePago.EFECTIVO);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al ingresar el monto de efectivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarTarjeta() {
        for (Venta venta : ventas) {
            venta.setFormaDePago(FormaDePago.TARJETA);
        }
        guardarVenta();
    }

    private void pagarMixto() {
        try {
            double efectivo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto pagado en efectivo:"));
            double tarjeta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto pagado con tarjeta:"));
            double total = Double.parseDouble(totalField.getText());
            if (efectivo + tarjeta < total) {
                JOptionPane.showMessageDialog(this, "El monto total ingresado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar la forma de pago para las ventas
            for (Venta venta : ventas) {
                venta.setFormaDePago(FormaDePago.MIXTO);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al ingresar los montos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
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
