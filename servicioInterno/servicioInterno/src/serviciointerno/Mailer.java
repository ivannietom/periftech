package serviciointerno;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
    
    public Mailer() {}

    public void sendMail(String infoPedido, String correo) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.put("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        propiedad.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "periftech@gmail.com";
        String password = "periftech1234";
        String receptor = correo;

        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
            mail.setSubject("Información de tu pedido en PerifTech");
            mail.setText(infoPedido);

            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia, password);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transportar.close(); 
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        } 
    }
}