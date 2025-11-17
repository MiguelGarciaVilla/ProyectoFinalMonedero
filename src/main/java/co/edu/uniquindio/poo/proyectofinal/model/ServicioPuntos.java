package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.*;

public class ServicioPuntos {
    private final List<Beneficio> beneficiosDisponibles;

    public ServicioPuntos() {
        this.beneficiosDisponibles = new ArrayList<>();
        cargarBeneficios();
    }

    private void cargarBeneficios() {
        beneficiosDisponibles.add(new Beneficio("B100", "Bono de $10", 100, 10.0));
        beneficiosDisponibles.add(new Beneficio("B500", "Bono de $50", 500, 50.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $100", 1000, 100.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $200", 2000, 200.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $300", 3000, 300.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $400", 4000, 400.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $500", 5000, 500.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $600", 6000, 600.0));
        beneficiosDisponibles.add(new Beneficio("B1000", "Bono de $700", 7000, 700.0));
    }

    public Collection<Beneficio> getBeneficiosDisponibles() {
        return beneficiosDisponibles;
    }

    public boolean canjearBeneficio(Cliente cliente, Beneficio beneficio) {
        if (beneficio == null) {
            return false;
        }
        if (cliente.getPuntos() >= beneficio.getPuntosRequeridos()) {
            cliente.restarPuntos(beneficio.getPuntosRequeridos());
            return true;
        } else {
            return false;
        }
    }


}






