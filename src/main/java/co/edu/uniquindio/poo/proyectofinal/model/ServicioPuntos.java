package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.HashMap;
import java.util.Map;

public class ServicioPuntos {
    private final Map<String, Beneficio> beneficiosDisponibles;

    public ServicioPuntos() {
        this.beneficiosDisponibles = new HashMap<>();
        cargarBeneficios();
    }

    private void cargarBeneficios() {
        beneficiosDisponibles.put("B100", new Beneficio("B100", "10% de descuento en comisión de transferencias", 100));
        beneficiosDisponibles.put("B500", new Beneficio("B500", "Un mes sin cargos por retiros", 500));
        beneficiosDisponibles.put("B1000", new Beneficio("B1000", "Bono de saldo de 50 unidades", 1000));
    }

    public void mostrarBeneficios() {
        System.out.println("--- Beneficios Disponibles para Canjear ---");
        for (Beneficio b : beneficiosDisponibles.values()) {
            System.out.printf("- ID: %s (%d Puntos): %s\n",
                    b.getIdBeneficio(), b.getPuntosRequeridos(), b.getDescripcion());
        }
        System.out.println("\n");
    }

    public void canjearBeneficio(Cliente cliente, String idBeneficio) {
        Beneficio beneficio = beneficiosDisponibles.get(idBeneficio);
        if (beneficio == null) {
            System.out.println("Error: El beneficio con ID " + idBeneficio + " no existe.");
            return;
        }
        if (cliente.getPuntos() < beneficio.getPuntosRequeridos()) {
            System.out.printf("Canje Fallido: %s no tiene suficientes puntos para '%s'. (Req: %d, Tiene: %d)\n", cliente.getNombre(), beneficio.getDescripcion(), beneficio.getPuntosRequeridos(), cliente.getPuntos());
            return;
        }

        boolean exito = cliente.restarPuntos(beneficio.getPuntosRequeridos());
        if (exito) {
            System.out.printf("¡Canje Exitoso! %s ha obtenido '%s'.\n", cliente.getNombre(), beneficio.getDescripcion());
            System.out.printf("Puntos restantes de %s: %d\n", cliente.getNombre(), cliente.getPuntos());
        }
    }
}
