package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RegistroView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;

    private UsuarioController usuarioController;

    // Constructor
    public RegistroView(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;

        setTitle("Sistema de Ventas - Registro");
        setSize(300, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Etiqueta y campo para el nombre de usuario
        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setBounds(20, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        // Etiqueta y campo para la contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(20, 60, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        // Etiqueta y combo para seleccionar el rol
        JLabel roleLabel = new JLabel("Rol:");
        roleLabel.setBounds(20, 100, 80, 25);
        add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"vendedor", "admin"});
        roleComboBox.setBounds(100, 100, 160, 25);
        add(roleComboBox);

        // Botón para registrar el usuario
        registerButton = new JButton("Registrar");
        registerButton.setBounds(100, 150, 160, 25);
        add(registerButton);

        // Acción del botón
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        // Centrar la ventana
        setLocationRelativeTo(null);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para registrar un nuevo usuario
    private void registrarUsuario() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        // Verificar si los campos están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }

        // Crear el nuevo objeto Usuario
        Usuario nuevoUsuario = new Usuario(username, password, role);

        try {
            // Registrar el usuario en la base de datos usando el método adecuado
            boolean registrado = usuarioController.registrarUsuario(nuevoUsuario);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                dispose(); // Cerrar la ventana de registro
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.");
            }
        } catch (IllegalArgumentException ex) {
            // Si los campos no son válidos, mostrar un mensaje de error
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}