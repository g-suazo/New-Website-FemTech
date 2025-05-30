package com.femtech.empresa.service;

import com.femtech.empresa.model.Message;
import com.femtech.empresa.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EmailService emailService;

    @Autowired
    public MessageService(MessageRepository messageRepository, EmailService emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    // obtener mensajes paginados
    public Page<Message> getMessagesPaginated(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    // buscar mensajes por nombre del cliente
    public Page<Message> findMessagesByClientName(String clientName, Pageable pageable) {
        return messageRepository.findByClientNameContaining(clientName, pageable);
    }

    // guardar mensaje con validaciones
    public void saveMessage(Message message) {
        if (message.getClientName() == null || message.getClientName().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ error: el nombre del cliente es obligatorio.");
        }
        if (message.getClientEmail() == null || message.getClientEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ error: el correo electrónico es obligatorio.");
        }
        if (message.getSubject() == null || message.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ error: el asunto es obligatorio.");
        }
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ error: el contenido del mensaje es obligatorio.");
        }

        // guardar en base de datos
        messageRepository.save(message);

        // enviar notificación a la empresa y copia al cliente
        sendNotificationEmail(message);
        sendCopyToClient(message);
    }

    // enviar correo a la empresa
    private void sendNotificationEmail(Message message) {
        String to = "info@femtechcr.com"; // correo de la empresa
        String subject = "Nuevo mensaje de contacto de " + message.getClientName();
        String body = """
                Has recibido un nuevo mensaje de contacto:
                
                Nombre: %s
                Correo: %s
                Asunto: %s
                Mensaje: %s
                
                Por favor, responde lo antes posible.
                """.formatted(message.getClientName(), message.getClientEmail(), message.getSubject(), message.getContent());

        emailService.sendContactEmail(to, subject, body, message.getClientEmail());
    }

    // enviar copia al cliente
    private void sendCopyToClient(Message message) {
        String to = message.getClientEmail();
        String subject = "Copia de tu mensaje a FemTech";
        String body = """
                Hola %s,
                
                Hemos recibido tu mensaje y te responderemos lo antes posible.
                
                Aquí tienes una copia de tu consulta:
                
                Asunto: %s
                Mensaje: %s
                
                Gracias por contactarnos.
                
                Atentamente,
                El equipo de FemTech.
                """.formatted(message.getClientName(), message.getSubject(), message.getContent());

        emailService.sendContactEmail(to, subject, body, "info@femtechcr.com");
    }

    // eliminar mensaje con validación
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new IllegalArgumentException("❌ error: el mensaje con ID " + id + " no existe.");
        }
        messageRepository.deleteById(id);
    }

    // actualizar mensaje con validaciones
    public Message updateMessage(Long id, Message updatedMessage) {
        return messageRepository.findById(id)
                .map(existingMessage -> {
                    if (updatedMessage.getClientName() != null) {
                        existingMessage.setClientName(updatedMessage.getClientName());
                    }
                    if (updatedMessage.getClientEmail() != null) {
                        existingMessage.setClientEmail(updatedMessage.getClientEmail());
                    }
                    if (updatedMessage.getSubject() != null) {
                        existingMessage.setSubject(updatedMessage.getSubject());
                    }
                    if (updatedMessage.getContent() != null) {
                        existingMessage.setContent(updatedMessage.getContent());
                    }
                    return messageRepository.save(existingMessage);
                })
                .orElseThrow(() -> new IllegalArgumentException("❌ error: mensaje con ID " + id + " no encontrado."));
    }

    // verificar si un mensaje existe
    public boolean existsById(Long id) {
        return messageRepository.existsById(id);
    }
}
