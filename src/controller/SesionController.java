package controller;

import model.BaseDatos;
import model.Usuario;
import model.Vendedor;

public class SesionController {
    private BaseDatos baseDatos;       // Referencia a la base de datos
    private Usuario usuarioActual;     // Usuario actualmente autenticado

    // Constructor principal con BaseDatos como parámetro
    public SesionController(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    // Obtener el usuario actualmente autenticado
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Iniciar sesión con un usuario
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    // Cerrar sesión
    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    // Validar las credenciales del usuario (unificado para usuarios y vendedores)
    public Usuario autenticar(String username, String password) {
        Usuario usuario = baseDatos.obtenerUsuario(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            iniciarSesion(usuario);
            return usuario;
        }
        return null;
    }

    // Verificar si el usuario autenticado es un vendedor
    public boolean esVendedor(String username) {
        Usuario usuario = baseDatos.obtenerUsuario(username);
        return usuario != null && "vendedor".equals(usuario.getRole());
    }

    // Método que devuelve el vendedor si las credenciales son correctas
    public Vendedor iniciarSesionVendedor(String nombreUsuario, String password) {
        // Aquí obtenemos el vendedor usando las credenciales proporcionadas
        for (Vendedor vendedor : baseDatos.getVendedores()) {
            if (vendedor.getNombre().equals(nombreUsuario) && vendedor.getPassword().equals(password)) {
                return vendedor; // Si las credenciales coinciden, devolvemos el vendedor
            }
        }
        return null; // Si no se encuentra el vendedor, devolvemos null
    }
}
