package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

public class GestorProgramador {
    private final List<TransaccionProgramada> colaDeTransacciones;
    private final ServicioTransacciones servicioTransacciones;

    public GestorProgramador(ServicioTransacciones servicioTransacciones) {
        this.colaDeTransacciones = new ArrayList<>();
        this.servicioTransacciones = servicioTransacciones;
    }

    public void programarTransaccion(TransaccionProgramada transaccionProgramada) {
        this.colaDeTransacciones.add(transaccionProgramada);
        System.out.printf("[Gestor] Nueva transaccion programada (%s) para el %s.\n",
                transaccionProgramada.getId(), transaccionProgramada.getFechaEjecucion().toString());
    }

    public void procesarCola(LocalDate fechaActual) {
        System.out.printf("\n--- PROCESANDO COLA DE TRANSACCIONES PROGRAMADAS (Día: %s) ---\n", fechaActual.toString());

        colaDeTransacciones.sort(Comparator.comparing(TransaccionProgramada::getFechaEjecucion));

        Iterator<TransaccionProgramada> iterador = colaDeTransacciones.iterator();
        while (iterador.hasNext()) {
            TransaccionProgramada transaccionProgramada = iterador.next();

            if (!transaccionProgramada.getFechaEjecucion().isAfter(fechaActual)) {
                System.out.printf("Ejecutando transaccion programada: %s...\n", transaccionProgramada.getId());

                servicioTransacciones.realizarTransferencia(
                        transaccionProgramada.getClienteOrigen(),
                        transaccionProgramada.getMonederoOrigen(),
                        null,
                        transaccionProgramada.getMonederoDestino(),
                        transaccionProgramada.getMonto(),
                        transaccionProgramada.getFechaEjecucion()
                );

                if (transaccionProgramada.getPeriodicidad() == Periodicidad.UNICA) {
                    iterador.remove();
                } else if (transaccionProgramada.getPeriodicidad() == Periodicidad.MENSUAL) {
                    transaccionProgramada.setFechaEjecucion(fechaActual.plusMonths(1));
                    System.out.printf("Transaccion (%s) reprogramada para %s.\n",
                            transaccionProgramada.getId(), transaccionProgramada.getFechaEjecucion().toString());
                } else if (transaccionProgramada.getPeriodicidad() == Periodicidad.SEMANAL) {
                    transaccionProgramada.setFechaEjecucion(fechaActual.plusWeeks(1));
                }
            } else {
                break;
            }
        }
    }
}
