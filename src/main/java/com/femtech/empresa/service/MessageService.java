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

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // nuevo método para paginación
    public Page<Message> getMessagesPaginated(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    // nuevo método para buscar mensajes por nombre del cliente
    public Page<Message> findMessagesByClientName(String clientName, Pageable pageable) {
        return messageRepository.findByClientNameContaining(clientName, pageable);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
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

