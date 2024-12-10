package model;

public class Usuario {
    private String username;
    String password;
    private String role;

    // Constructor con tres parámetros
    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor sobrecargado con dos parámetros (username y password)
    public Usuario(String username, String password) {
        this(username, password, "usuario"); // Asigna un rol por defecto, por ejemplo "usuario"
    }

    // Método para verificar si el usuario es válido
    public boolean esValido() {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty() && role != null && !role.isEmpty();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
