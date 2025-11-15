package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Retiro extends Transaccion {
    private final Monedero monedero;
    public Retiro(Monedero monedero, double valor, LocalDate fecha) {
        super(valor,fecha);
        this.monedero = monedero;
    }

    @Override
    public void ejecutar() {
        boolean exito = monedero.restarSaldo(this.valor);
        if (exito) {
            this.estado = EstadoTransaccion.COMPLETADA;
        } else {
            this.estado = EstadoTransaccion.FALLIDA;
        }
    }

    @Override
    public int calcularPuntos() {
        return (int) (this.valor / 100) * 2;
    }

}
