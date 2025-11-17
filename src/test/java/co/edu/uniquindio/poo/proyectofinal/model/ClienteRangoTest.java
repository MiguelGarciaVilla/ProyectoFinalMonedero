package co.edu.uniquindio.poo.proyectofinal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteRangoTest {

    @Test
    @DisplayName("Cliente debe promocionar de BRONCE a PLATA")
    void promocionARangoPlata() {

        Cliente cliente = new Cliente("C001", "Test", "a", "miguel", "1234");
        assertEquals(Rango.BRONCE, cliente.getRango(), "El cliente debe empezar en BRONCE");
        assertEquals(0, cliente.getPuntos());

        cliente.agregarPuntos(500);

        assertEquals(500, cliente.getPuntos());
        assertEquals(Rango.BRONCE, cliente.getRango(), "Con 500 puntos exactos, sigue en BRONCE");

        cliente.agregarPuntos(1);

        assertEquals(501, cliente.getPuntos());
        assertEquals(Rango.PLATA, cliente.getRango(), "Con 501 puntos, debe ser PLATA");
    }

    @Test
    @DisplayName("Cliente debe promocionar directamente a ORO")
    void promocionDirectaAOro() {

        Cliente cliente = new Cliente("C001", "Test", "b", "a", "a");
        assertEquals(Rango.BRONCE, cliente.getRango());

        cliente.agregarPuntos(1500);

        assertEquals(1500, cliente.getPuntos());
        assertEquals(Rango.ORO, cliente.getRango(), "Con 1500 puntos, debe ser ORO (1001-5000)");
    }
}
