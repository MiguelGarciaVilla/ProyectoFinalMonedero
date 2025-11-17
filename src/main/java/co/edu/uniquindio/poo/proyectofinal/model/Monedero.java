package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Monedero {
    private final String idMonedero;
    private double saldo;
    private final String tipo;
    private final List<Transaccion> historialTransacciones;


    public Monedero(String idMonedero, double saldo, String tipo) {
        this.idMonedero = idMonedero;
        this.saldo = saldo;
        this.tipo = tipo;
        this.historialTransacciones = new ArrayList<>();
    }

    public String getIdMonedero() {
        return idMonedero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Transaccion> getHistorialTransacciones() {
        return historialTransacciones;
    }

    public Transaccion getUltimaTransaccion() {
        if (historialTransacciones.isEmpty()) {
            return null;
        }
        return historialTransacciones.get(historialTransacciones.size() - 1);
    }
    public boolean validarSaldoSuficiente(double monto) {
        return this.saldo >= monto;
    }

    public void agregarSaldo(double monto) {
        this.saldo += monto;
    }

    public boolean restarSaldo(double monto) {
        if (validarSaldoSuficiente(monto)) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    public void agregarTransaccionHistorial(Transaccion t) {
        Transaccion ultima = getUltimaTransaccion();
        String hashAnterior = (ultima == null) ? "PRIMER_TRANSACCION" : ultima.getHashTransaccion();
        t.setHashAnterior(hashAnterior);
        this.historialTransacciones.add(t);
    }

    @Override
    public String toString() {
        return String.format("Monedero [ID: %s, Tipo: %s, Saldo: %.2f]", idMonedero, tipo, saldo);
    }



}
