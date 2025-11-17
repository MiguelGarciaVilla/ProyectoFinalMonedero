package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private final String idCliente;
    private String nombre;
    private final List<Monedero> monederos;
    private int puntos;
    private Rango rango;
    private String email;
    private String usuario;
    private String password;

    public Cliente(String idCliente, String nombre, String email,String usuario, String password) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.monederos = new ArrayList<>();
        this.puntos = 0;
        this.rango = Rango.BRONCE;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void agregarMonedero(Monedero m) {
        this.monederos.add(m);
    }
    public void agregarPuntos(int puntosGanados) {
        this.puntos += puntosGanados;
        actualizarRango();
    }
    private void actualizarRango() {
        this.rango = Rango.determinarRango(this.puntos);
    }

    public boolean restarPuntos(int puntosGastados) {
        if (this.puntos >= puntosGastados) {
            this.puntos -= puntosGastados;
            actualizarRango();
            return true;
        }
        return false;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Monedero> getMonederos() {
        return monederos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }
}
