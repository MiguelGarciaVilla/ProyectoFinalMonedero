package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class TransaccionProgramada {
    private String id;
    private Cliente clienteOrigen;
    private Monedero monederoOrigen;
    private Monedero monederoDestino;
    private double monto;
    private LocalDate fechaEjecucion;
    private Periodicidad periodicidad;

    public TransaccionProgramada(Cliente cOrigen, Monedero mOrigen, Monedero mDestino, double monto, LocalDate fecha, Periodicidad p) {
        this.id = java.util.UUID.randomUUID().toString();
        this.clienteOrigen = cOrigen;
        this.monederoOrigen = mOrigen;
        this.monederoDestino = mDestino;
        this.monto = monto;
        this.fechaEjecucion = fecha;
        this.periodicidad = p;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Cliente getClienteOrigen() {
        return clienteOrigen;
    }
    public Monedero getMonederoOrigen() {
        return monederoOrigen;
    }
    public Monedero getMonederoDestino() {
        return monederoDestino;
    }
    public double getMonto() {
        return monto;
    }
    public Periodicidad getPeriodicidad() {
        return periodicidad;
    }
    public String getId() {
        return id;
    }
}
