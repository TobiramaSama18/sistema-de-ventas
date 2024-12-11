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

    private BaseDatos baseDatos;
    private SesionController sesionController;
    private Vendedor vendedor;

    public VendedorView(BaseDatos baseDatos, SesionController sesionController, Vendedor vendedor) {
        this.baseDatos = baseDatos;
        this.sesionController = sesionController;
        this.vendedor = vendedor;

        setTitle("Sistema de Ventas - Vendedor: " + vendedor.getNombre());
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel bienvenidaLabel = new JLabel("Bienvenido, " + vendedor.getNombre());
        bienvenidaLabel.setBounds(120, 30, 200, 30);
        add(bienvenidaLabel);

        iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.setBounds(120, 100, 150, 30);
        add(iniciarSesionButton);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        setLocationRelativeTo(null); // Centrar ventana
        setVisible(true);
    }

    private void cerrarSesion() {
        sesionController.cerrarSesion();
        // Redirigir al sistema de ventas
        new SistemaVentasView(baseDatos, sesionController, vendedor).setVisible(true);
        dispose();  // Cerrar la ventana de vendedor
    }
}
