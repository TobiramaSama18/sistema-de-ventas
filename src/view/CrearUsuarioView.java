package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearUsuarioView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> rolComboBox;
    private JButton registrarButton;
    private JButton cancelarButton;

    private UsuarioController usuarioController;

    // Constructor para la vista de creación de usuario
    public CrearUsuarioView(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;

        // Configuración de la ventana
        setTitle("Registrar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Etiqueta de título
        JLabel tituloLabel = new JLabel("Registro de Usuario");
        tituloLabel.setBounds(120, 20, 160, 25);
        add(tituloLabel);

        // Campo para ingresar el nombre de usuario
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 180, 25);
        add(usernameField);

        // Campo para ingresar la contraseña
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);
        add(passwordField);

        // Campo para seleccionar el rol
        JLabel rolLabel = new JLabel("Rol:");
        rolLabel.setBounds(50, 150, 80, 25);
        add(rolLabel);

        rolComboBox = new JComboBox<>(new String[]{"vendedor", "admin"});
        rolComboBox.setBounds(150, 150, 180, 25);
        add(rolComboBox);

        // Botón para registrar usuario
        registrarButton = new JButton("Registrar");
        registrarButton.setBounds(50, 200, 120, 30);
        add(registrarButton);

        // Botón para cancelar y cerrar la ventana
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(210, 200, 120, 30);
        add(cancelarButton);

        // Acción para registrar un nuevo usuario
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        // Acción para cerrar la ventana sin registrar un usuario
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para registrar un usuario
    private void registrarUsuario() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String rol = (String) rolComboBox.getSelectedItem();

        // Verificar que los campos no estén vacíos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        // Crear el objeto Usuario con los datos ingresados
        Usuario nuevoUsuario = new Usuario(username, password, rol);

        // Llamar al controlador para registrar el usuario
        try {
            boolean registrado = usuarioController.registrarUsuario(nuevoUsuario);

            // Mostrar mensaje según el resultado del registro
            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                dispose(); // Cerrar la ventana después del registro
            } else {
                JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe. Intente con otro.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}

