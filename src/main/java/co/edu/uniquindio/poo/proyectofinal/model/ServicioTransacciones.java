package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioTransacciones {
    private final List<INotificador> notificadores;

    public ServicioTransacciones() {
        this.notificadores = new ArrayList<>();
        this.notificadores.add(new NotificadorEmail());
    }

    public void registrarNotificador(INotificador notificador) {
        this.notificadores.add(notificador);
    }

    public void realizarDeposito(Cliente cliente, Monedero destino, double monto, LocalDate fecha) {
        System.out.printf("Procesando DEPÓSITO de %.2f para %s...\n", monto, cliente.getNombre());
        Deposito d = new Deposito(destino, monto, fecha);
        procesar(cliente, d, destino);
    }

    public void realizarRetiro(Cliente cliente, Monedero origen, double monto, LocalDate fecha) {
        System.out.printf("Procesando RETIRO de %.2f para %s...\n", monto, cliente.getNombre());
        Retiro r = new Retiro(origen, monto,  fecha);
        procesar(cliente, r, origen);
    }

    public void realizarTransferencia(Cliente cOrigen, Monedero mOrigen, Cliente cDestino, Monedero mDestino, double monto, LocalDate fecha) {
        System.out.printf("Procesando TRANSFERENCIA de %.2f de %s a %s...\n",
                monto, cOrigen.getNombre(), cDestino.getNombre());

        Transferencia t = new Transferencia(mOrigen, mDestino, monto, fecha);
        procesar(cOrigen, t, mOrigen, mDestino);
    }


    private void procesar(Cliente cliente, Transaccion t, Monedero m) {
        t.ejecutar();

        if (t.getEstado() == EstadoTransaccion.COMPLETADA) {
            m.agregarTransaccionHistorial(t);

            int puntos = t.calcularPuntos();
            cliente.agregarPuntos(puntos);

            String msg = String.format("Transacción %s exitosa por %.2f. Ganaste %d puntos. Saldo actual: %.2f.",
                    t.getClass().getSimpleName(), t.getValor(), puntos, m.getSaldo());
            enviarNotificaciones(cliente, msg);

        } else if (t.getEstado() == EstadoTransaccion.FALLIDA) {
            String msg = String.format("Falló tu %s por %.2f. Saldo actual: %.2f.",
                    t.getClass().getSimpleName(), t.getValor(), m.getSaldo());
            enviarNotificaciones(cliente, msg);
        }
    }


    private void procesar(Cliente cliente, Transaccion t, Monedero mOrigen, Monedero mDestino) {
        t.ejecutar();

        if (t.getEstado() == EstadoTransaccion.COMPLETADA) {

            mOrigen.agregarTransaccionHistorial(t);
            mDestino.agregarTransaccionHistorial(t);

            int puntos = t.calcularPuntos();
            cliente.agregarPuntos(puntos);

            String msg = String.format("Transferencia exitosa por %.2f. Ganaste %d puntos. Saldo actual: %.2f.",
                    t.getValor(), puntos, mOrigen.getSaldo());
            enviarNotificaciones(cliente, msg);

        } else if (t.getEstado() == EstadoTransaccion.FALLIDA) {
            String msg = String.format("Falló tu Transferencia por %.2f. Saldo actual: %.2f.",
                    t.getValor(), mOrigen.getSaldo());
            enviarNotificaciones(cliente, msg);
        }
    }

    private void enviarNotificaciones(Cliente cliente, String mensaje) {
        for (INotificador notificador : notificadores) {
            notificador.enviarNotificacion(cliente, mensaje);
        }
    }
}
