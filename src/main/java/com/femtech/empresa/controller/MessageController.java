package com.femtech.empresa.controller;

import com.femtech.empresa.model.MessageRequestDTO;
import com.femtech.empresa.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")

public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDTO request) {
        service.sendMessage(request);
        return ResponseEntity.ok("message sent successfully");
    }
}
