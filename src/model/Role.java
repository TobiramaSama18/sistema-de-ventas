package model;

public enum Role {
    ADMIN("admin"),
    VENDEDOR("vendedor");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

