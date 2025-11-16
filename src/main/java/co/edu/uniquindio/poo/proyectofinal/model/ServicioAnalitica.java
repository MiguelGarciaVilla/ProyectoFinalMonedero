package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;
import java.util.List;

public class ServicioAnalitica {

    public String generarReporteGasto(Cliente cliente, LocalDate inicio, LocalDate fin) {
        StringBuilder reporte = new StringBuilder();
        reporte.append(String.format(
                "--- Reporte de Patrones de Gasto para: %s (%s a %s) ---\n",
                cliente.getNombre(), inicio.toString(), fin.toString()
        ));
        double totalGastado = 0;
        int numTransaccionesGasto = 0;
        for (Monedero m : cliente.getMonederos()) {
            for (Transaccion t : m.getHistorialTransacciones()) {
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
        reporte.append(String.format("Total Gastado: $%.2f\n", totalGastado));
        reporte.append(String.format("Número de Gastos: %d\n", numTransaccionesGasto));
        if (numTransaccionesGasto > 0) {
            reporte.append(String.format("Gasto Promedio por Transacción: $%.2f\n", (totalGastado / numTransaccionesGasto)));
        } else {
            reporte.append("No se registraron gastos en este periodo.\n");
        }
        reporte.append("\n");

        return reporte.toString();
    }
}