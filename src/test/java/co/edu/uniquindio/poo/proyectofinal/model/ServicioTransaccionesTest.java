package co.edu.uniquindio.poo.proyectofinal.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ServicioTransaccionesTest {

    private Cliente clienteA;
    private Cliente clienteB;
    private Monedero monederoA;
    private Monedero monederoB;
    private ServicioTransacciones servicio;

    @BeforeEach
    void setUp() {
        clienteA = new Cliente("C001", "Miguel", "a");
        clienteB = new Cliente("C002", "Luis", "b");

        monederoA = new Monedero("M-MIGUEL-01",1000 , "Ahorros");
        monederoB = new Monedero("M-LUIS-01", 500, "Diario");

        clienteA.agregarMonedero(monederoA);
        clienteB.agregarMonedero(monederoB);

        servicio = new ServicioTransacciones();
    }

    @Test
    @DisplayName("Debe transferir fondos y calcular puntos correctamente")
    void realizarTransferenciaExitosa() {
        servicio.realizarTransferencia(clienteA, monederoA, clienteB, monederoB, 300.0, LocalDate.now());
        assertEquals(700.0, monederoA.getSaldo(), "El saldo de Ana debe ser 700");
        assertEquals(800.0, monederoB.getSaldo(), "El saldo de Luis debe ser 800");

        assertEquals(9, clienteA.getPuntos(), "Miguel debe tener 9 puntos");
        assertEquals(0, clienteB.getPuntos(), "Luis no debe ganar puntos");

        assertEquals(1, monederoA.getHistorialTransacciones().size(), "Historial de Ana debe tener 1 tx");
        assertEquals(1, monederoB.getHistorialTransacciones().size(), "Historial de Luis debe tener 1 tx");
    }

    @Test
    @DisplayName("No debe permitir retiro con fondos insuficientes")
    void realizarRetiroFallido() {
        servicio.realizarRetiro(clienteA, monederoA, 1500.0, LocalDate.now());

        assertEquals(1000.0, monederoA.getSaldo(), "El saldo de Ana no debe cambiar");

        assertEquals(0, clienteA.getPuntos(), "Ana no debe ganar puntos");
    }
}