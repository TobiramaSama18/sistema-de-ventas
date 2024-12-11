package view;

import controller.SesionController;
import controller.UsuarioController;
import model.BaseDatos;
import model.Usuario;
import model.Vendedor;
import model.Role;

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
            Usuario usuario = baseDatos.autenticar(username, password);

            if (usuario != null) {
                sesionController.iniciarSesion(usuario);  // Iniciar sesión del usuario
                dispose();  // Cerrar la ventana de login

                System.out.println("Usuario autenticado: " + usuario.getUsername());

                // Comprobar el rol del usuario y redirigir según corresponda
                if (Role.ADMIN.equals(usuario.getRole())) {
                    // Redirigir a la vista del administrador
                    new AdministradorView(baseDatos, sesionController).setVisible(true);  // Vista del administrador
                } else if (Role.VENDEDOR.equals(usuario.getRole())) {
                    // Crear un objeto Vendedor para pasarlo a SistemaVentasView
                    Vendedor vendedor = new Vendedor(usuario.getUsername(), usuario.getPassword(), usuario.getRole(), "Descripción del Vendedor");
                    // Redirigir al sistema de ventas del vendedor
                    new SistemaVentasView(baseDatos, sesionController, vendedor).setVisible(true);  // Vista del vendedor
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCrearUsuario() {
        // Crear un controlador para la vista de creación de usuario
        UsuarioController usuarioController = new UsuarioController(baseDatos);
        new CrearUsuarioView(usuarioController).setVisible(true);
    }

    public static void main(String[] args) {
        // Inicializar la base de datos y el controlador de sesión
        BaseDatos baseDatos = new BaseDatos();
        SesionController sesionController = new SesionController(baseDatos);

        // Lanzar la vista de login
        SwingUtilities.invokeLater(() -> new LoginView(baseDatos, sesionController).setVisible(true));
    }
}