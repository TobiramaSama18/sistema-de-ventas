
package controller;

import model.BaseDatos;
import model.Usuario;

public class UsuarioController {
    private BaseDatos baseDatos;

    // Constructor para inicializar la base de datos
    public UsuarioController(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * 
     * @param nuevoUsuario el objeto Usuario con los datos del usuario.
     * @return true si el usuario se registró correctamente, false si el nombre de usuario ya existe.
     */
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        // Validar que el objeto Usuario no sea nulo y que los campos sean válidos
        if (nuevoUsuario == null || !nuevoUsuario.esValido()) {
            throw new IllegalArgumentException("Los campos username, password y rol no pueden estar vacíos.");
        }

        // Intentar registrar al nuevo usuario en la base de datos
        return baseDatos.registrarUsuario(nuevoUsuario);
    }

    /**
     * Autentica un usuario en la base de datos.
     * 
     * @param username el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return el usuario autenticado si las credenciales son correctas, null en caso contrario.
     */
    public Usuario autenticarUsuario(String username, String password) {
        // Validar que los parámetros no sean nulos o vacíos
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Los campos username y password no pueden estar vacíos.");
        }

        // Llamar al método de BaseDatos para autenticar al usuario
        return baseDatos.autenticar(username, password);
    }
}