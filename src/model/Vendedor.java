package model;

public class Vendedor extends Usuario {

    private String descripcion; // Descripción del vendedor

    // Constructor de Vendedor, que recibe los parámetros y pasa los necesarios a la clase Usuario
    public Vendedor(String username, String password, String role, String descripcion) {
        super(username, password, role); // Llamada al constructor de la clase Usuario
        this.descripcion = descripcion;
        
    }

    // Getter y setter para la descripción
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Si deseas un método para obtener el nombre explícito, puedes agregar uno
    public String getNombre() {
        return getUsername(); // Devuelve el nombre del vendedor (en este caso el username)
    }
    
    public String getPassword (){
        return password;
    }
   
}