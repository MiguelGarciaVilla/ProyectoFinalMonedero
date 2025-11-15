package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Deposito extends Transaccion{
    private final Monedero monedero;

    public Deposito(Monedero monedero, double valor, LocalDate fecha) {
        super(valor, fecha);
        this.monedero = monedero;
    }

    @Override
    public void ejecutar() {
        monedero.agregarSaldo(this.valor);
        this.estado = EstadoTransaccion.COMPLETADA;
    }

    @Override
    public int calcularPuntos() {
        return (int) (this.valor / 100);
    }



}
