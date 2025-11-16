package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public class Bono extends Transaccion {
    private final Monedero monederoDestino;

    public Bono(Monedero monederoDestino, double monto, LocalDate fecha) {
        super(monto, fecha);
        this.monederoDestino = monederoDestino;
    }

    @Override
    public void ejecutar() {
        monederoDestino.agregarSaldo(this.valor);
        this.estado = EstadoTransaccion.COMPLETADA;
    }

    @Override
    public int calcularPuntos() {
        return 0;
    }
}
