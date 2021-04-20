package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mailer {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String datosCorreo) {

    	String mensaje = datosCorreo;
        String partes[] = mensaje.split("-");

        String nombreCliente = partes[0];
        String correoCliente = partes[1];
        String direccionCliente = partes[2];
        String precioPedido = partes[3];
        String fechaLlegada = partes[4];
         
        String infoPedido = "Nombre cliente: " + nombreCliente +"\n"
        		+ "Correo cliente: " + correoCliente + "\n"
        		+ "Dirección a la que será enviado: " + direccionCliente + "\n"
        		+ "Precio del pedido: " + precioPedido + "\n"
        		+ "Fecha prevista de llegada: " + fechaLlegada;
        
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom("periftech@gmail.com");
        mail.setTo(correoCliente);
        mail.setSubject("Resumen de tu pedido periftech, " + nombreCliente);
        mail.setText(infoPedido);

        javaMailSender.send(mail);
    }
}
