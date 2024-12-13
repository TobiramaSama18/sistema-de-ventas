package model;

public enum Role {
    ADMINISTRADOR("admin"),  // Cambio de 'ADMIN' a 'ADMINISTRADOR'
    VENDEDOR("vendedor"),
    USUARIO("usuario");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
