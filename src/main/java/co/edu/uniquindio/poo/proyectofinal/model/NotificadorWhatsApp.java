package co.edu.uniquindio.poo.proyectofinal.model;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class NotificadorWhatsApp implements INotificador{
    public static final String ACCOUNT_SID = "AC45f78ed16b157bfffa59ebb506c1175d";
    public static final String AUTH_TOKEN = "fbd5c644e3c7816a09d1035dfa679b15";
    private static final String FROM_NUMBER = "whatsapp:+14155238886";
    private static final String TO_NUMBER = "whatsapp:+573183110437";

    public NotificadorWhatsApp() {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        } catch (Exception e) {
            System.err.println("Error inicializando Twilio.");
            e.printStackTrace();
        }
    }

    @Override
    public void enviarNotificacion(Cliente cliente, String mensaje) {
        System.out.println("[ENVIANDO WHATSAPP]");
        try {
            Message message = Message.creator(
                    new PhoneNumber(TO_NUMBER),
                    new PhoneNumber(FROM_NUMBER),
                    mensaje
            ).create();
            System.out.println("Mensaje de WhatsApp enviado. SID: " + message.getSid());

        } catch (Exception e) {
            System.err.println("Error al enviar el WhatsApp: " + e.getMessage());
        }
    }
}
