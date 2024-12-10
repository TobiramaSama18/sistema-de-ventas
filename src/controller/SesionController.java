
package controller;

import model.BaseDatos;
import model.Vendedor;
import model.Usuario;

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

    // Validar las credenciales del usuario
    public boolean validarUsuario(String username, String password) {
        // Autenticar credenciales usando la base de datos
        Usuario usuario = baseDatos.autenticar(username, password);
        if (usuario != null) {
            iniciarSesion(usuario); // Si las credenciales son válidas, iniciar sesión
            return true;
        }
        return false; // Credenciales inválidas
    }

    // Verificar si el usuario autenticado es un vendedor
    public boolean esVendedor(String username) {
        Usuario usuario = baseDatos.obtenerUsuario(username);
        return usuario != null && "vendedor".equals(usuario.getRole()); // Comprobar el tipo de usuario
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