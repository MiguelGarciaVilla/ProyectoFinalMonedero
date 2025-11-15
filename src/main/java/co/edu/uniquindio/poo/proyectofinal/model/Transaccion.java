package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaccion {
    protected String id;
    protected LocalDate fecha;
    protected double valor;
    protected EstadoTransaccion estado;
    protected String hashTransaccion;
    protected String hashAnterior;

    public Transaccion(double valor, LocalDate fecha) {
        this.id = UUID.randomUUID().toString();
        this.fecha = fecha;
        this.valor = valor;
        this.estado= EstadoTransaccion.PENDIENTE;
        this.hashTransaccion = String.valueOf(this.id.hashCode() * (long) (valor * 100));
    }

    public abstract void ejecutar();

    public abstract int calcularPuntos();

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setEstado(EstadoTransaccion estado) {
        this.estado = estado;
    }

    public void setHashTransaccion(String hashTransaccion) {
        this.hashTransaccion = hashTransaccion;
    }

    public void setHashAnterior(String hashAnterior) {
        this.hashAnterior = hashAnterior;
    }

    public String getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getValor() {
        return valor;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public String getHashTransaccion() {
        return hashTransaccion;
    }

    public String getHashAnterior() {
        return hashAnterior;
    }
}
