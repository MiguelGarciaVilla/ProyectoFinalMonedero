package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.List;

public class ServicioAnalitica {

    public void generarReporteGasto(Cliente cliente, LocalDate inicio, LocalDate fin) {
        System.out.printf("\n--- Reporte de Patrones de Gasto para: %s (%s a %s) ---\n",
                cliente.getNombre(), inicio, fin);

        double totalGastado = 0;
        int numTransaccionesGasto = 0;

        for (Monedero m : cliente.getMonederos()) {
            List<Transaccion> historial = m.getHistorialTransacciones();

            for (Transaccion t : historial) {
                LocalDate fechaTx = t.getFecha();
                if (fechaTx.isBefore(inicio) || fechaTx.isAfter(fin)) {
                    continue;
                }

                boolean esGasto = false;
                if (t instanceof Retiro) {
                    esGasto = true;
                } else if (t instanceof Transferencia) {
                    if (((Transferencia) t).getMonederoOrigen() == m) {
                        esGasto = true;
                    }
                }

                if (esGasto) {
                    totalGastado += t.getValor();
                    numTransaccionesGasto++;
                }
            }
        }

        System.out.printf("Total Gastado: %.2f\n", totalGastado);
        System.out.printf("Número de Gastos: %d\n", numTransaccionesGasto);
        if (numTransaccionesGasto > 0) {
            System.out.printf("Gasto Promedio por Transacción: %.2f\n", (totalGastado / numTransaccionesGasto));
        } else {
            System.out.println("No se registraron gastos en este periodo."+"\n");
        }
    }
}