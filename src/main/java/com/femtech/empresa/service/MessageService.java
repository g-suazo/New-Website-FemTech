package com.femtech.empresa.service;

import com.femtech.empresa.model.Message;
import com.femtech.empresa.model.MessageRequestDTO;
import com.femtech.empresa.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public void sendMessage(MessageRequestDTO request) {
        Message message = new Message();
        message.setClientName(request.clientName());
        message.setClientEmail(request.clientEmail());
        message.setSubject(request.subject());
        message.setContent(request.content());
        repository.save(message);
    }

    public List<Message> getAllMessages() {
        return repository.findAll();
    }
}

