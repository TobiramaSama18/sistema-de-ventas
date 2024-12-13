package model;

public enum FormaDePago {
    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta"),
    TRANSFERENCIA("Transferencia"),
    MIXTO("Mixto");  // Agregada la opción MIXTO

    private final String nombre;

    // Constructor del enum con validación
    FormaDePago(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la forma de pago no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

    // Getter para obtener el nombre
    public String getNombre() {
        return nombre;
    }

    // Sobrescribir el método toString para devolver el nombre de forma legible
    @Override
    public String toString() {
        return nombre;
    }

    // Método para obtener una forma de pago por nombre, útil si la necesitas en algún lugar de tu código
    public static FormaDePago fromString(String nombre) {
        for (FormaDePago forma : FormaDePago.values()) {
            if (forma.nombre.equalsIgnoreCase(nombre)) {
                return forma;
            }
        }
        throw new IllegalArgumentException("Forma de pago no válida: " + nombre);
    }
}
