package com.femtech.empresa.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${EMAIL_USERNAME}") // obtiene el correo del remitente desde las variables de entorno
    private String senderEmail;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTestEmail() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo("destinatario@example.com");
            helper.setSubject("Correo de prueba desde Spring Boot");
            helper.setText("¡Este es un correo de prueba para verificar la configuración!");

            mailSender.send(message);
            System.out.println("Correo enviado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public void sendContactEmail(String to, String subject, String body, String replyTo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false); // false = texto plano

            // Configurar "Reply-To" solo si es un correo válido
            if (replyTo != null && replyTo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                helper.setReplyTo(new InternetAddress(replyTo));
            }

            mailSender.send(message);
            System.out.println("Correo enviado con éxito a: " + to);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
