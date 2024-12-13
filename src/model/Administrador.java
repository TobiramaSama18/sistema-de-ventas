package model;

public class Administrador {

    private String username;
    private String password;

    // Constructor de Administrador
    public Administrador(String username, String password) {
        this.username = username;
        this.password = password;
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

    // Método para validar que los campos no sean nulos o vacíos
    public boolean esValido() {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    // Método para mostrar la información del administrador
    public void mostrarInformacion() {
        System.out.println("Administrador: " + getUsername());
    }
}
