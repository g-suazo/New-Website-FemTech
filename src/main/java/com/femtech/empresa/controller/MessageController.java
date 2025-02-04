package com.femtech.empresa.controller;

import com.femtech.empresa.model.Message;
import com.femtech.empresa.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "http://127.0.0.1:8081") // permite solicitudes desde el frontend
@RestController
@RequestMapping("/api/messages")
@Tag(name = "Mensajes", description = "Operaciones relacionadas con los mensajes")
@Validated
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Obtiene mensajes con paginación", description = "Permite obtener una lista paginada de mensajes. Opcionalmente, puedes filtrar por el nombre del cliente.")
    @GetMapping
    public Page<Message> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String clientName) {
        Pageable pageable = PageRequest.of(page, size);
        if (clientName != null && !clientName.trim().isEmpty()) {
            return messageService.findMessagesByClientName(clientName, pageable);
        }
        return messageService.getMessagesPaginated(pageable);
    }

    @Operation(summary = "Crea un nuevo mensaje", description = "Registra un nuevo mensaje en la base de datos.")
    @PostMapping
    public ResponseEntity<String> saveMessage(@Valid @RequestBody Message message) {
        messageService.saveMessage(message);
        return ResponseEntity.ok("Mensaje guardado con éxito");
    }

    @Operation(summary = "Elimina un mensaje", description = "Elimina un mensaje de la base de datos usando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok("Mensaje eliminado con éxito");
    }

}
