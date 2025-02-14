package Modelos;

public class VeterinarioDTO {

    private int id;
    private String nif;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "VeterinarioDTO{"
                + "id=" + id
                + ", nif='" + nif + '\''
                + ", nombre='" + nombre + '\''
                + ", direccion='" + direccion + '\''
                + ", telefono='" + telefono + '\''
                + ", email='" + email + '\''
                + '}';
    }

}
