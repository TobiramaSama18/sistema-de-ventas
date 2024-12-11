package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDatos {
    private Map<String, Usuario> usuarios;     // Mapa para gestionar los usuarios
    private Map<String, Vendedor> vendedores;  // Mapa para gestionar los vendedores
    private Map<String, Venta> ventas;         // Mapa para gestionar las ventas

    private List<Usuario> listaUsuarios;       // Lista para usuarios (también para verificación)
    private List<Vendedor> listaVendedores;    // Lista para los vendedores

    public BaseDatos() {
        usuarios = new HashMap<>();
        vendedores = new HashMap<>();
        ventas = new HashMap<>();
        
        listaUsuarios = new ArrayList<>();
        listaVendedores = new ArrayList<>();

        // Crear algunos usuarios de ejemplo
        Usuario admin = new Usuario("admin", "1234", "admin");
        Usuario adrian = new Usuario("adrian", "1234", "vendedor");
        usuarios.put("admin", admin);
        usuarios.put("adrian", adrian);
        listaUsuarios.add(admin);
        listaUsuarios.add(adrian);

        // Crear algunos vendedores de ejemplo
        Vendedor vendedor = new Vendedor("adrian", "1234", "vendedor", "Vendedor de tienda");
        vendedores.put("adrian", vendedor);
        listaVendedores.add(vendedor);
    }

    /**
     * Método para autenticar a un usuario con su nombre y contraseña.
     * @param username el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return el usuario autenticado si las credenciales son correctas, null en caso contrario.
     */
    public Usuario autenticar(String username, String password) {
        Usuario user = usuarios.get(username); // Buscar al usuario por su nombre
        if (user != null && user.getPassword().equals(password)) {
            return user; // Si existe y la contraseña es correcta, retornar el usuario
        }
        return null; // Si no existe o la contraseña es incorrecta, retornar null
    }

    /**
     * Obtener un vendedor asociado a un usuario.
     * @param usuario el usuario cuyo vendedor se quiere obtener.
     * @return el vendedor asociado al usuario.
     */
    public Vendedor obtenerVendedorPorUsuario(Usuario usuario) {
        if (usuario == null) {
            return null; // Validación de si el usuario es null
        }
        return vendedores.get(usuario.getUsername()); // Retorna el vendedor correspondiente al nombre de usuario
    }

    /**
     * Obtener un usuario por su nombre.
     * @param nombreUsuario el nombre de usuario.
     * @return el usuario o null si no existe.
     */
    public Usuario obtenerUsuario(String nombreUsuario) {
        return usuarios.get(nombreUsuario); // Retorna el usuario o null si no existe
    }

    /**
     * Registrar un nuevo usuario.
     * @param usuario el usuario a registrar.
     * @return true si el usuario se registró correctamente, false si ya existe.
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getUsername())) {
            return false; // Si el usuario ya existe, no lo agrega
        }
        usuarios.put(usuario.getUsername(), usuario); // Agrega el nuevo usuario
        listaUsuarios.add(usuario); // También lo agrega a la lista
        return true;
    }

    /**
     * Agregar una nueva venta.
     * @param venta la venta a agregar.
     */
    public void agregarVenta(Venta venta) {
        if (venta == null || ventas.containsKey(venta.getCodigo())) {
            return; // Validar si la venta ya existe o si es null
        }
        ventas.put(venta.getCodigo(), venta); // Usar el código de la venta como clave
    }

    /**
     * Obtener una venta por su código.
     * @param codigoVenta el código de la venta.
     * @return la venta correspondiente al código.
     */
    public Venta obtenerVenta(String codigoVenta) {
        return ventas.get(codigoVenta); // Obtener la venta por su código
    }

    /**
     * Obtener todas las ventas como lista.
     * @return una lista con todas las ventas.
     */
    public List<Venta> getVentas() {
        return new ArrayList<>(ventas.values()); // Convertir las ventas a una lista
    }

    /**
     * Obtener la última venta registrada.
     * @return la última venta o null si no hay ventas.
     */
    public Venta getUltimaVenta() {
        if (ventas.isEmpty()) {
            return null; // Si no hay ventas, retornar null
        }
        // Iterar sobre las ventas para obtener la última
        return ventas.values().stream().reduce((first, second) -> second).orElse(null);
    }

    /**
     * Obtener todos los vendedores.
     * @return la lista de vendedores.
     */
    public List<Vendedor> getVendedores() {
        return listaVendedores; // Retorna la lista de vendedores
    }

    /**
     * Obtener todos los usuarios.
     * @return la lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return listaUsuarios; // Retorna la lista de usuarios
    }
}