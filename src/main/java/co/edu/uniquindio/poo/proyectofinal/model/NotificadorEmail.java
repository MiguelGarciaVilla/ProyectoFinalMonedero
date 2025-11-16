package co.edu.uniquindio.poo.proyectofinal.model;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificadorEmail implements INotificador {

    private final String GMAIL_USERNAME = "lumigavi25@gmail.com";
    private final String GMAIL_APP_PASSWORD = "zzak edkt byyv ntmo";
    private final Properties props;
    private final Session session;

    public NotificadorEmail() {
        this.props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_USERNAME, GMAIL_APP_PASSWORD);
            }
        });
    }

    @Override
    public void enviarNotificacion(Cliente cliente, String mensaje) {
        String emailDestino = cliente.getEmail();
        System.out.println("[ENVIANDO EMAIL]");

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(GMAIL_USERNAME));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            mimeMessage.setSubject("Notificación de tu Monedero Virtual");
            mimeMessage.setText("Hola " + cliente.getNombre() + ",\n\n" + mensaje);

            Transport.send(mimeMessage);

            System.out.println("Email enviado exitosamente a " + emailDestino);
            System.out.println("\n");

        } catch (MessagingException e) {
            System.err.println("Error al enviar el email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
