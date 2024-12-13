package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDatos {
    private Map<String, Vendedor> vendedores;      // Mapa para gestionar los vendedores
    private List<Venta> ventas;                    // Lista para gestionar las ventas
    private Map<String, Administrador> administradores;  // Mapa para gestionar los administradores

    public BaseDatos() {
        vendedores = new HashMap<>();
        ventas = new ArrayList<>();
        administradores = new HashMap<>();

        // Crear un administrador por defecto
        Administrador administrador = new Administrador("admin", "1234");
        administradores.put("admin", administrador);

        // Crear un vendedor por defecto (se asume que Vendedor tiene un constructor con 2 parámetros)
        Vendedor vendedor = new Vendedor("adrian", "1234");  // Solo dos parámetros en el constructor
        vendedor.setDescripcion("Vendedor experimentado");   // Esto asume que el método setDescripcion existe en la clase Vendedor
        vendedores.put("adrian", vendedor);
    }

    
    //Método para obtener un Administrador por credenciales.
    private Administrador obtenerAdministradorPorCredenciales(String username, String password) {
        Administrador administrador = administradores.get(username);
        if (administrador != null && administrador.getPassword().equals(password)) {
            return administrador;
        }
        return null;
    }

    
     //Método para obtener un Vendedor por credenciales.
    private Vendedor obtenerVendedorPorCredenciales(String username, String password) {
        Vendedor vendedor = vendedores.get(username);
        if (vendedor != null && vendedor.getPassword().equals(password)) {
            return vendedor;
        }
        return null;
    }

        //Método para autenticar a un vendedor o administrador con su nombre y contraseña.
    public Object autenticar(String username, String password) {
        // Verificar si es administrador
        Administrador admin = obtenerAdministradorPorCredenciales(username, password);
        if (admin != null) {
            return admin;
        }

        // Verificar si es vendedor
        Vendedor vendedor = obtenerVendedorPorCredenciales(username, password);
        if (vendedor != null) {
            return vendedor;
        }

        return null;  // Si no se encuentra el usuario o la contraseña es incorrecta
    }

    //Agregar una nueva venta.
    public void agregarVenta(Venta venta) {
        if (venta != null) {
            ventas.add(venta);
        }
    }


     //Obtener una venta por su posición en la lista.
    public Venta obtenerVenta(int index) {
        if (index >= 0 && index < ventas.size()) {
            return ventas.get(index);
        }
        return null;
    }

    //Obtener todas las ventas como lista.
    public List<Venta> getVentas() {
        return ventas;
    }

    //Obtener la última venta registrada.
    public Venta getUltimaVenta() {
        if (ventas.isEmpty()) {
            return null;
        }
        return ventas.get(ventas.size() - 1);
    }

    //Obtener todos los vendedores.
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores.values());
    }

    //Obtener todos los administradores.
    public List<Administrador> getAdministradores() {
        return new ArrayList<>(administradores.values());
    }


    //Registrar un nuevo vendedor.
    public void registrarVendedor(Vendedor vendedor) {
        if (vendedor != null) {
            vendedores.put(vendedor.getUsername(), vendedor);
        }
    }

   //Registrar un nuevo administrador.
    public void registrarAdministrador(Administrador administrador) {
        if (administrador != null) {
            administradores.put(administrador.getUsername(), administrador);
        }
    }

    // Método para guardar ventas
    public void guardarVentas(ArrayList<Venta> ventas) {
        if (ventas != null) {
            // Añadir todas las ventas a la lista interna
            this.ventas.addAll(ventas);
        }
    }

    // Métodos para obtener Vendedores y Administradores por username
    public Vendedor obtenerVendedor(String username) {
        return vendedores.get(username);
    }

    public Administrador obtenerAdministrador(String username) {
        return administradores.get(username);
    }
}
