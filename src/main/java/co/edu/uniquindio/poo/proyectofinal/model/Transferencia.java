package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Transferencia extends Transaccion{
    private final Monedero monederoOrigen;
    private final Monedero monederoDestino;

    public Transferencia(Monedero monederoOrigen, Monedero monederoDestino, double valor, LocalDate fecha) {
        super(valor, fecha);
        this.monederoOrigen = monederoOrigen;
        this.monederoDestino = monederoDestino;
    }

    public Monedero getMonederoOrigen() {
        return monederoOrigen;
    }

    public Monedero getMonederoDestino() {
        return monederoDestino;
    }

    @Override
    public void ejecutar() {
        if (!monederoOrigen.validarSaldoSuficiente(this.valor)) {
            this.estado = EstadoTransaccion.FALLIDA;
            return;
        }
        boolean exitoRetiro = monederoOrigen.restarSaldo(this.valor);
        if (exitoRetiro) {
            monederoDestino.agregarSaldo(this.valor);
            this.estado = EstadoTransaccion.COMPLETADA;
        } else {
            this.estado = EstadoTransaccion.FALLIDA;
        }
    }

    @Override
    public int calcularPuntos() {
        return (int) (this.valor / 100) * 3;
    }




}
