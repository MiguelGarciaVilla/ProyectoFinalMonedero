package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

public class GestorProgramador {
    private final List<TransaccionProgramada> colaDeTransacciones;
    private final ServicioTransacciones servicioTransacciones; // El servicio que sabe cómo ejecutar

    public GestorProgramador(ServicioTransacciones servicioTransacciones) {
        this.colaDeTransacciones = new ArrayList<>();
        this.servicioTransacciones = servicioTransacciones;
    }

    public void programarTransaccion(TransaccionProgramada tx) {
        this.colaDeTransacciones.add(tx);
        System.out.printf("[Gestor] Nueva transaccion programada (%s) para el %s.\n",
                tx.getId(), tx.getFechaEjecucion().toString());
    }

    public void procesarCola(LocalDate fechaActual) {
        System.out.printf("\n--- PROCESANDO COLA DE TRANSACCIONES PROGRAMADAS (Día: %s) ---\n", fechaActual.toString());

        colaDeTransacciones.sort(Comparator.comparing(TransaccionProgramada::getFechaEjecucion));

        Iterator<TransaccionProgramada> iterador = colaDeTransacciones.iterator();
        while (iterador.hasNext()) {
            TransaccionProgramada tx = iterador.next();

            if (!tx.getFechaEjecucion().isAfter(fechaActual)) {
                System.out.printf("Ejecutando transaccion programada: %s...\n", tx.getId());

                servicioTransacciones.realizarTransferencia(
                        tx.getClienteOrigen(),
                        tx.getMonederoOrigen(),
                        null,
                        tx.getMonederoDestino(),
                        tx.getMonto(),
                        tx.getFechaEjecucion()
                );

                if (tx.getPeriodicidad() == Periodicidad.UNICA) {
                    iterador.remove();
                } else if (tx.getPeriodicidad() == Periodicidad.MENSUAL) {
                    tx.setFechaEjecucion(fechaActual.plusMonths(1));
                    System.out.printf("Transaccion (%s) reprogramada para %s.\n",
                            tx.getId(), tx.getFechaEjecucion().toString());
                } else if (tx.getPeriodicidad() == Periodicidad.SEMANAL) {
                    tx.setFechaEjecucion(fechaActual.plusWeeks(1));
                }
            } else {
                break;
            }
        }
    }
}
