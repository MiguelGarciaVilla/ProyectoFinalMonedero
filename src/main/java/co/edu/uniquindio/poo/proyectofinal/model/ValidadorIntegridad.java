package co.edu.uniquindio.poo.proyectofinal.model;


import java.util.List;

public class ValidadorIntegridad {

    public boolean verificarHistorial(Monedero m) {
        List<Transaccion> historial = m.getHistorialTransacciones();
        if (historial.isEmpty()) {
            return true;
        }
        return verificarRecursivo(historial, historial.size() - 1);
    }

    private boolean verificarRecursivo(List<Transaccion> historial, int index) {
        if (index == 0) {
            Transaccion primeraTx = historial.get(0);
            return primeraTx.getHashAnterior().equals("GENESIS_BLOCK");
        }
        Transaccion txActual = historial.get(index);
        Transaccion txAnterior = historial.get(index - 1);
        String hashCalculadoAnterior = txAnterior.getHashTransaccion();

        boolean esValido = txActual.getHashAnterior().equals(hashCalculadoAnterior);
        return esValido && verificarRecursivo(historial, index - 1);
    }
}




