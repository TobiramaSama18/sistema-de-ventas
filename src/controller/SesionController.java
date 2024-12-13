package controller;

import model.BaseDatos;
import model.Vendedor;
import model.Administrador;
import view.SistemaVentasView;  // Vista para el vendedor
import view.SistemaAdministradorView;  // Vista para el administrador
import view.LoginView;  // Vista para el login
import javax.swing.*;

public class SesionController {
    private BaseDatos baseDatos;             // Referencia a la base de datos
    private Vendedor vendedorActual;         // Vendedor logueado
    private Administrador administradorActual; // Administrador logueado
    private JFrame ventanaActual;            // Para manejar la ventana actual

    // Constructor principal con BaseDatos como parámetro
    public SesionController(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
        System.out.println("SesionController inicializado con baseDatos.");
    }

    // Obtener el vendedor logueado
    public Vendedor getVendedorActual() {
        return vendedorActual;
    }

    // Obtener el administrador logueado
    public Administrador getAdministradorActual() {
        return administradorActual;
    }

    // Iniciar sesión de vendedor
    public boolean iniciarSesion(Vendedor vendedor) {
        try {
            if (vendedor != null) {
                this.vendedorActual = vendedor; // Almacenar el vendedor actual
                System.out.println("Vendedor autenticado: " + vendedor.getNombre());
                return true;
            } else {
                System.out.println("Vendedor no autenticado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al intentar iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    // Iniciar sesión de administrador
    public boolean iniciarSesion(Administrador administrador) {
        try {
            if (administrador != null) {
                this.administradorActual = administrador; // Almacenar el administrador actual
                System.out.println("Administrador autenticado: " + administrador.getUsername());
                return true;
            } else {
                System.out.println("Administrador no autenticado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al intentar iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    // Cerrar sesión
    public void cerrarSesion() {
        System.out.println("Cerrando sesión...");
        this.vendedorActual = null;
        this.administradorActual = null;
        if (ventanaActual != null) {
            ventanaActual.dispose();  // Cierra la ventana actual
            System.out.println("Ventana actual cerrada.");
        }
    }

    // Método para autenticar un vendedor
    public Vendedor autenticarVendedor(String username, String password) {
        System.out.println("Autenticando vendedor...");
        for (Vendedor vendedor : baseDatos.getVendedores()) {
            if (vendedor.getNombre().equals(username) && vendedor.getPassword().equals(password)) {
                this.vendedorActual = vendedor;
                System.out.println("Vendedor autenticado: " + username);
                return vendedor;
            }
        }
        System.out.println("Vendedor no encontrado o contraseña incorrecta.");
        return null;
    }

    // Método para autenticar al administrador
    public Administrador autenticarAdministrador(String username, String password) {
        System.out.println("Autenticando administrador...");
        for (Administrador admin : baseDatos.getAdministradores()) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                this.administradorActual = admin;
                System.out.println("Administrador autenticado: " + username);
                return admin;
            }
        }
        System.out.println("Administrador no encontrado o contraseña incorrecta.");
        return null;
    }

    // Verificar si el usuario autenticado es un vendedor
    public boolean esVendedor() {
        return vendedorActual != null;
    }

    // Verificar si el usuario autenticado es un administrador
    public boolean esAdministrador() {
        return administradorActual != null;
    }

    // Método para redirigir a la vista adecuada
    public void redirigirAlSistema(LoginView loginView) {
        System.out.println("Intentando redirigir...");

        // Verificar si el usuario es un vendedor
        if (esVendedor()) {
            System.out.println("Redirigiendo al sistema de ventas...");
            if (ventanaActual != null) {
                ventanaActual.dispose();  // Cierra la ventana de login
            }
            // Instanciamos la vista correspondiente para el vendedor
            SistemaVentasView ventasView = new SistemaVentasView(baseDatos, this, vendedorActual);
            ventasView.setVisible(true);  // Asegura que la vista de ventas esté visible
        }
        // Verificar si el usuario es un administrador
        else if (esAdministrador()) {
            System.out.println("Redirigiendo al sistema administrador...");
            if (ventanaActual != null) {
                ventanaActual.dispose();  // Cierra la ventana de login
            }
            // Instanciamos la vista correspondiente para el administrador
            SistemaAdministradorView adminView = new SistemaAdministradorView(baseDatos, this, administradorActual);
            adminView.setVisible(true);  // Asegura que la vista de administrador esté visible
        }
        // Si las credenciales son incorrectas
        else {
            System.out.println("Credenciales incorrectas.");
            JOptionPane.showMessageDialog(loginView, "Credenciales incorrectas");
        }
    }

    // Redirigir al login
    public void redirigirAlLogin(JFrame ventanaActual) {
        ventanaActual.dispose();  // Cierra la ventana actual
        System.out.println("Redirigiendo a la vista de login...");
        new LoginView(baseDatos, this).setVisible(true);  // Pasa los parámetros requeridos
    }
}
