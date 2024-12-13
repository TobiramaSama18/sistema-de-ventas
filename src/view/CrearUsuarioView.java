package view;

import controller.UsuarioController;
import model.Vendedor;  // Importamos Vendedor
import model.Administrador;  // Importamos Administrador
import model.Role;
import model.BaseDatos;
import controller.SesionController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CrearUsuarioView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> rolComboBox;
    private JButton registrarButton;
    private JButton cancelarButton;

    private UsuarioController usuarioController;
    private BaseDatos baseDatos;  // Debes pasar esta instancia a la vista
    private SesionController sesionController;  // Debes pasar esta instancia a la vista

    private LoginView loginView;  // Se añade referencia a la vista de login para redirigir al login después del registro

    // Constructor para la vista de creación de usuario
    public CrearUsuarioView(UsuarioController usuarioController, BaseDatos baseDatos, SesionController sesionController, LoginView loginView) {
        this.usuarioController = usuarioController;
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;
        this.loginView = loginView;  // Inicializamos la vista de login

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
        String rolString = (String) rolComboBox.getSelectedItem();

        // Verificar que los campos no estén vacíos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        // Verificar si el usuario ya existe
        if (usuarioController.obtenerVendedorPorUsername(username) != null || usuarioController.obtenerAdministradorPorUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya existe. Intente con otro.");
            return;
        }

        // Convertir el rol a la enumeración Role
        Role role = null;
        try {
            // Mapear el string del combo box a Role
            if (rolString.equalsIgnoreCase("admin")) {
                role = Role.ADMINISTRADOR;  // Mapeamos "admin" a ADMINISTRADOR
            } else if (rolString.equalsIgnoreCase("vendedor")) {
                role = Role.VENDEDOR;  // Mapeamos "vendedor" a VENDEDOR
            }

            // Verificar que el rol no sea nulo
            if (role == null) {
                JOptionPane.showMessageDialog(this, "Rol inválido seleccionado.");
                return;
            }

            // Crear un nuevo Vendedor o Administrador dependiendo del rol
            if (role == Role.ADMINISTRADOR) {
                Administrador nuevoAdministrador = new Administrador(username, password);
                // Llamar al controlador para registrar el administrador
                boolean registrado = usuarioController.registrarAdministrador(nuevoAdministrador);
                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Administrador registrado exitosamente.");
                    redirigirAlSistema(nuevoAdministrador);
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error al registrar el administrador. Intente nuevamente.");
                }
            } else if (role == Role.VENDEDOR) {
                Vendedor nuevoVendedor = new Vendedor(username, password);
                // Llamar al controlador para registrar el vendedor
                boolean registrado = usuarioController.registrarVendedor(nuevoVendedor);
                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Vendedor registrado exitosamente.");
                    redirigirAlSistema(nuevoVendedor);
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error al registrar el vendedor. Intente nuevamente.");
                }
            }

            // No es necesario llamar dispose aquí, ya que las vistas de redirección manejarán el cierre
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario: " + ex.getMessage());
        }
    }

    // Método para redirigir al sistema correspondiente según el rol
    private void redirigirAlSistema(Administrador nuevoAdministrador) {
        dispose(); // Cerrar ventana de registro
        loginView.setVisible(true); // Volver a mostrar el login después del registro
    }

    private void redirigirAlSistema(Vendedor nuevoVendedor) {
        dispose(); // Cerrar ventana de registro
        loginView.setVisible(true); // Volver a mostrar el login después del registro
    }
}
