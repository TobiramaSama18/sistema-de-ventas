package model;

public class Vendedor {

    private String username;
    private String password;
    private String descripcion;

    // Constructor de Vendedor con dos parámetros (añadir valor por defecto o nulo para descripcion)
    public Vendedor(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser vacío.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser vacía.");
        }
        this.username = username;
        this.password = password;
        this.descripcion = "";  // Asignar valor por defecto si no se pasa descripcion
    }

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para mostrar la información del vendedor
    public void mostrarInformacion() {
        System.out.println("Vendedor: " + getUsername());
        System.out.println("Descripción: " + (descripcion.isEmpty() ? "No disponible" : getDescripcion()));
    }

    // Método para verificar si los campos del vendedor son válidos
    public boolean esValido() {
        return username != null && !username.isEmpty() && 
               password != null && !password.isEmpty();
    }

    // Método para obtener el nombre del vendedor (esto puede usarse en vez de getUsername())
    public String getNombre() {
        return getUsername();
    }
}
