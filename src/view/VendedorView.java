package view;

import controller.SesionController;
import model.BaseDatos;
import model.Vendedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class VendedorView extends JFrame {
    private JButton iniciarSesionButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private BaseDatos baseDatos;
    private SesionController sesionController;

    public VendedorView(BaseDatos baseDatos, SesionController sesionController) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;

        setTitle("Sistema de Ventas - Vendedor");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel bienvenidaLabel = new JLabel("Bienvenido, Vendedor");
        bienvenidaLabel.setBounds(120, 30, 200, 30);
        add(bienvenidaLabel);

        JLabel usernameLabel = new JLabel("Nombre de Usuario:");
        usernameLabel.setBounds(50, 60, 150, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 60, 150, 30);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(50, 100, 150, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 100, 150, 30);
        add(passwordField);

        iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.setBounds(120, 140, 150, 30);
        add(iniciarSesionButton);

        // Acción del botón Iniciar Sesión
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Intentar autenticar al vendedor
                Vendedor vendedor = sesionController.autenticarVendedor(username, password);
                if (vendedor != null) {
                    // Si el vendedor se autentica correctamente, abrir la vista del sistema de ventas
                    new SistemaVentasView(baseDatos, sesionController, vendedor).setVisible(true);
                    dispose(); // Cerrar la vista de login
                } else {
                    // Si las credenciales no son correctas, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLocationRelativeTo(null); // Centrar ventana
        setVisible(true);
    }
}
