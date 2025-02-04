package com.femtech.empresa.service;

import com.femtech.empresa.model.Message;
import com.femtech.empresa.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EmailService emailService;

    @Autowired
    public MessageService(MessageRepository messageRepository, EmailService emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    public Page<Message> getMessagesPaginated(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Page<Message> findMessagesByClientName(String clientName, Pageable pageable) {
        return messageRepository.findByClientNameContaining(clientName, pageable);
    }

    public void saveMessage(Message message) {
        // guardar mensaje en la base de datos
        messageRepository.save(message);

        // enviar correo a la empresa y una copia al cliente
        sendNotificationEmail(message);
        sendCopyToClient(message);
    }

    private void sendNotificationEmail(Message message) {
        String to = "info@femtechcr.com"; // correo de la empresa
        String subject = "Nuevo mensaje de contacto de " + message.getClientName();
        String body = "Has recibido un nuevo mensaje de contacto:\n\n" +
                "Nombre: " + message.getClientName() + "\n" +
                "Correo: " + message.getClientEmail() + "\n" +
                "Asunto: " + message.getSubject() + "\n" +
                "Mensaje: " + message.getContent() + "\n\n" +
                "Por favor, responde lo antes posible.";

        // si el cliente puso un email válido, se usa como "Reply-To"
        emailService.sendContactEmail(to, subject, body, message.getClientEmail());
    }

    private void sendCopyToClient(Message message) {
        String to = message.getClientEmail(); // correo del cliente
        String subject = "Copia de tu mensaje a FemTech";
        String body = "Hola " + message.getClientName() + ",\n\n" +
                "Hemos recibido tu mensaje y te responderemos lo antes posible.\n\n" +
                "Aquí tienes una copia de tu consulta:\n\n" +
                "Asunto: " + message.getSubject() + "\n" +
                "Mensaje: " + message.getContent() + "\n\n" +
                "Gracias por contactarnos.\n\n" +
                "Atentamente,\n" +
                "El equipo de FemTech.";

        // el "Reply-To" será el correo de la empresa
        emailService.sendContactEmail(to, subject, body, "info@femtechcr.com");
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message updateMessage(Long id, Message updatedMessage) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setClientName(updatedMessage.getClientName());
                    message.setClientEmail(updatedMessage.getClientEmail());
                    message.setSubject(updatedMessage.getSubject());
                    message.setContent(updatedMessage.getContent());
                    return messageRepository.save(message);
                })
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }
}
