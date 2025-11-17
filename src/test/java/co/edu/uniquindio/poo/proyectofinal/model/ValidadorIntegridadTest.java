package co.edu.uniquindio.poo.proyectofinal.model;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorIntegridadTest {

    private Monedero monedero;
    private Cliente cliente;
    private ServicioTransacciones servicio;
    private ValidadorIntegridad validador;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("C001", "Test", "b", "a", "a");
        monedero = new Monedero("M-001", 1000, "Test");
        cliente.agregarMonedero(monedero);
        servicio = new ServicioTransacciones();
        validador = new ValidadorIntegridad();
    }

    @Test
    @DisplayName("Debe validar una cadena de historial íntegra")
    void validarHistorialIntegro() {

        servicio.realizarDeposito(cliente, monedero, 500.0, LocalDate.now());
        servicio.realizarRetiro(cliente, monedero, 200.0, LocalDate.now());
        servicio.realizarDeposito(cliente, monedero, 100.0, LocalDate.now());

        assertEquals(3, monedero.getHistorialTransacciones().size());
        boolean esValido = validador.verificarHistorial(monedero);
        assertTrue(esValido, "El validador debe reportar un historial válido");
    }

}
