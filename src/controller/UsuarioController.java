package controller;

import model.BaseDatos;
import model.Vendedor;
import model.Administrador;

public class UsuarioController {
    private BaseDatos baseDatos;

    // Constructor para inicializar la base de datos
    public UsuarioController(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    
    //Registra un nuevo vendedor en la base de datos.
     
    public boolean registrarVendedor(Vendedor nuevoVendedor) {
        // Validar que el objeto Vendedor no sea nulo y que los campos sean válidos
        if (nuevoVendedor == null || !nuevoVendedor.esValido()) {
            throw new IllegalArgumentException("Los campos username y password no pueden estar vacíos.");
        }

        // Intentar registrar al nuevo vendedor en la base de datos
        baseDatos.registrarVendedor(nuevoVendedor);
        return true;  // Asumimos que si no hubo excepciones, el registro fue exitoso
    }

   
     //Registra un nuevo administrador en la base de datos.
    public boolean registrarAdministrador(Administrador nuevoAdministrador) {
        // Validar que el objeto Administrador no sea nulo y que los campos sean válidos
        if (nuevoAdministrador == null || !nuevoAdministrador.esValido()) {
            throw new IllegalArgumentException("Los campos username y password no pueden estar vacíos.");
        }

        // Intentar registrar al nuevo administrador en la base de datos
        baseDatos.registrarAdministrador(nuevoAdministrador);
        return true;  // Asumimos que si no hubo excepciones, el registro fue exitoso
    }

    
     //Autentica un vendedor en la base de datos.
    public Vendedor autenticarVendedor(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Los campos username y password no pueden estar vacíos.");
        }

        // Llamar al método autenticar de BaseDatos que maneja tanto Vendedor como Administrador
        Object usuarioAutenticado = baseDatos.autenticar(username, password);
        if (usuarioAutenticado instanceof Vendedor) {
            return (Vendedor) usuarioAutenticado;
        }
        return null;  // Si no es un vendedor, se retorna null
    }

    
     //Autentica un administrador en la base de datos.
    public Administrador autenticarAdministrador(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Los campos username y password no pueden estar vacíos.");
        }

        // Llamar al método autenticar de BaseDatos que maneja tanto Vendedor como Administrador
        Object usuarioAutenticado = baseDatos.autenticar(username, password);
        if (usuarioAutenticado instanceof Administrador) {
            return (Administrador) usuarioAutenticado;
        }
        return null;  // Si no es un administrador, se retorna null
    }

    
     //Obtiene un vendedor por su nombre de usuario.
    public Vendedor obtenerVendedorPorUsername(String username) {
        return baseDatos.obtenerVendedor(username);
    }

      //Obtiene un administrador por su nombre de usuario.
    public Administrador obtenerAdministradorPorUsername(String username) {
        return baseDatos.obtenerAdministrador(username);
    }
}
