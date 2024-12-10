package model;

import controller.SesionController;
import view.LoginView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear la base de datos
            BaseDatos baseDatos = new BaseDatos();
            
            // Crear el controlador de sesión con la base de datos
            SesionController sesionController = new SesionController(baseDatos);

            // Lanzar la ventana de login en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView(baseDatos, sesionController);
                loginView.setVisible(true);
            });
        } catch (Exception e) {
            // Manejo de excepciones: imprimir detalles del error
            e.printStackTrace(); 
        }
    }
}
