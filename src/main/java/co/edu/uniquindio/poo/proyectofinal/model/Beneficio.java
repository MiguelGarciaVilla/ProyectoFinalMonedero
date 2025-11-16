package co.edu.uniquindio.poo.proyectofinal.model;

public class Beneficio {
    private  String idBeneficio;
    private  String descripcion;
    private  int puntosRequeridos;
    private double montoBono;

    public Beneficio(String idBeneficio, String descripcion, int puntosRequeridos,  double montoBono) {
        this.idBeneficio = idBeneficio;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
        this.montoBono = montoBono;
    }

    public void setIdBeneficio(String idBeneficio) {
        this.idBeneficio = idBeneficio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntosRequeridos(int puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public String getIdBeneficio() {
        return idBeneficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public double getMontoBono() {
        return montoBono;
    }
}
