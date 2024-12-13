package view;

import controller.SesionController;
import controller.UsuarioController;
import model.BaseDatos;
import model.Administrador;
import model.Vendedor;
import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginView extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton crearUsuarioButton;

    private BaseDatos baseDatos;
    private SesionController sesionController;

    public LoginView(BaseDatos baseDatos, SesionController sesionController) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;

        setTitle("Login");
        setSize(320, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel tituloLabel = new JLabel("INICIAR SESION");
        tituloLabel.setFont(new Font("Serif", Font.BOLD, 25));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBounds(70, 2, 200, 30);
        add(tituloLabel);

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setBounds(30, 50, 80, 25);
        add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(120, 50, 150, 25);
        add(usernameTextField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(30, 90, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 90, 150, 25);
        add(passwordField);

        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBounds(30, 140, 120, 30);
        add(loginButton);

        loginButton.addActionListener(e -> iniciarSesion());

        crearUsuarioButton = new JButton("Crear Usuario");
        crearUsuarioButton.setBounds(170, 140, 120, 30);
        add(crearUsuarioButton);

        crearUsuarioButton.addActionListener(e -> abrirCrearUsuario());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarSesion() {
        try {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());

            // Intentar autenticar al usuario
            Object usuario = baseDatos.autenticar(username, password);

            if (usuario != null) {
                // Iniciar sesión del usuario
                if (usuario instanceof Vendedor) {
                    sesionController.iniciarSesion((Vendedor) usuario);  // Iniciar sesión del vendedor
                } else if (usuario instanceof Administrador) {
                    sesionController.iniciarSesion((Administrador) usuario);  // Iniciar sesión del administrador
                }

                dispose();  // Cerrar la ventana de login

                System.out.println("Usuario autenticado: " + username);

                // Redirigir al sistema correspondiente según el rol
                redirigirAlSistema(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void redirigirAlSistema(Object usuario) {
        // Dependiendo del rol, se redirige a la vista correspondiente
        if (usuario instanceof Administrador) {
            // Crear un Usuario específico para el rol ADMINISTRADOR
            Administrador admin = (Administrador) usuario;
            SistemaAdministradorView adminView = new SistemaAdministradorView(baseDatos, sesionController, admin);
            adminView.setVisible(true);  // Usar el 'usuario' para la vista de administrador
        } else if (usuario instanceof Vendedor) {
            // Verificar si el usuario es un Vendedor
            Vendedor vendedor = (Vendedor) usuario;
            SistemaVentasView ventasView = new SistemaVentasView(baseDatos, sesionController, vendedor);
            ventasView.setVisible(true);  // Usar el 'vendedor' para la vista de ventas
        } else {
            JOptionPane.showMessageDialog(this, "Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCrearUsuario() {
        // Crear un controlador para la vista de creación de usuario
        UsuarioController usuarioController = new UsuarioController(baseDatos);

        // Ahora pasamos también los parámetros BaseDatos, SesionController y LoginView
        new CrearUsuarioView(usuarioController, baseDatos, sesionController, this).setVisible(true);
    }

    public static void main(String[] args) {
        // Inicializar la base de datos y el controlador de sesión
        BaseDatos baseDatos = new BaseDatos();
        SesionController sesionController = new SesionController(baseDatos);

        // Lanzar la vista de login
        SwingUtilities.invokeLater(() -> new LoginView(baseDatos, sesionController).setVisible(true));
    }
}
