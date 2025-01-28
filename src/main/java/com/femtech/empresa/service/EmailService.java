package com.femtech.empresa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("destinatario@example.com"); // reemplaza con tu correo de prueba
        message.setSubject("Correo de prueba desde Spring Boot");
        message.setText("¡Este es un correo de prueba para verificar la configuración!");

        try {
            mailSender.send(message);
            System.out.println("Correo enviado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

     //Método para enviar correos dinámicos (como los del formulario)
    public void sendContactEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // destinatario dinámico
        message.setSubject(subject); // asunto dinámico
        message.setText(body); // contenido dinámico

        try {
            mailSender.send(message);
            System.out.println("Correo enviado con éxito a: " + to);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
