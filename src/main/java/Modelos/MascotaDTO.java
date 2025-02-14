package Modelos;

import java.util.Date;

public class MascotaDTO {

    private int id;
    private String numeroChip;
    private String nombre;
    private double peso;
    private Date fechaNacimiento;
    private String tipo;
    private Integer idVeterinario; 

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(String numeroChip) {
        this.numeroChip = numeroChip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    @Override
    public String toString() {
        return "MascotaDTO{"
                + "id=" + id
                + ", numeroChip='" + numeroChip + '\''
                + ", nombre='" + nombre + '\''
                + ", peso=" + peso
                + ", fechaNacimiento=" + fechaNacimiento
                + ", tipo='" + tipo + '\''
                + ", idVeterinario=" + idVeterinario
                + '}';
    }

}
