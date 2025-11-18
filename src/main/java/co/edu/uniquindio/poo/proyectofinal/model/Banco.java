package co.edu.uniquindio.poo.proyectofinal.model;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    private static Banco instancia;
    private final List<Cliente> clientes;
    private List<Monedero>  monederos;
    private List<Beneficio> beneficios;
    private List<Transaccion> transacciones;
    private List<TransaccionProgramada> transaccionProgramadas;


    public Banco() {
        System.out.println(">>> Creando NUEVO Banco");
        clientes = new ArrayList<>();
        cargarDatosPrueba();
    }

    public static Banco getInstancia() {
        if (instancia == null) {
            instancia = new Banco();
        }
        return instancia;
    }

    private void cargarDatosPrueba() {
        Cliente c1 = new Cliente("C001", "Miguel Garcia", "lumigavi25@gmail.com", "miguel", "1234");
        c1.agregarMonedero(new Monedero("MIG-01", 1500.0, "Ahorros"));
        c1.agregarMonedero(new Monedero("MIG-02", 300.0, "Gastos Diarios"));

        Cliente c2 = new Cliente("C002", "Juan Arias", "email@juan.com", "juan", "4321");
        c2.agregarMonedero(new Monedero("JUAN-01", 500.0, "Gastos Diarios"));


        clientes.add(c1);
        clientes.add(c2);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente autenticar(String usuario, String password) {
        for (Cliente c : clientes) {
            if (c.getUsuario().equals(usuario) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }
}