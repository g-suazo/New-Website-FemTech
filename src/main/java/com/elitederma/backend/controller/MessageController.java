package com.femtech.empresa.controller;

import com.femtech.empresa.model.Message;
import com.femtech.empresa.service.MessageService;
import com.femtech.empresa.service.RecaptchaService;
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

import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:8081") // permite solicitudes desde el frontend
@RestController
@RequestMapping("/api/messages")
@Tag(name = "Mensajes", description = "Operaciones relacionadas con los mensajes")
@Validated
public class MessageController {

    private final MessageService messageService;
    private final RecaptchaService recaptchaService;

    @Autowired
    public MessageController(MessageService messageService, RecaptchaService recaptchaService) {
        this.messageService = messageService;
        this.recaptchaService = recaptchaService;
    }

    @Operation(summary = "Obtiene mensajes con paginación", description = "Obtiene una lista paginada de mensajes. Puedes filtrar por el nombre del cliente opcionalmente.")
    @GetMapping
    public ResponseEntity<Page<Message>> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String clientName) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = (clientName != null && !clientName.trim().isEmpty())
                ? messageService.findMessagesByClientName(clientName, pageable)
                : messageService.getMessagesPaginated(pageable);

        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Crea un nuevo mensaje", description = "Registra un mensaje en la base de datos con validación de reCAPTCHA.")
    @PostMapping
    public ResponseEntity<String> saveMessage(@Valid @RequestBody Map<String, Object> request) {
        try {
            // obtener valores del JSON
            String clientName = (String) request.get("clientName");
            String clientEmail = (String) request.get("clientEmail");
            String subject = (String) request.get("subject");
            String content = (String) request.get("content");
            String recaptchaToken = (String) request.get("recaptchaToken");

            // validar reCAPTCHA
            if (recaptchaToken == null || !recaptchaService.validateRecaptcha(recaptchaToken)) {
                return ResponseEntity.badRequest().body("❌ error: reCAPTCHA no válido.");
            }

            // validar datos básicos
            if (clientName == null || clientEmail == null || subject == null || content == null) {
                return ResponseEntity.badRequest().body("❌ error: todos los campos son obligatorios.");
            }

            // crear y guardar el mensaje
            Message message = new Message();
            message.setClientName(clientName.trim());
            message.setClientEmail(clientEmail.trim());
            message.setSubject(subject.trim());
            message.setContent(content.trim());

            messageService.saveMessage(message);

            return ResponseEntity.ok("✅ mensaje guardado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ error interno del servidor: " + e.getMessage());
        }
    }

    @Operation(summary = "Elimina un mensaje", description = "Elimina un mensaje de la base de datos usando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        if (!messageService.existsById(id)) {
            return ResponseEntity.badRequest().body("❌ error: el mensaje con ID " + id + " no existe.");
        }
        messageService.deleteMessage(id);
        return ResponseEntity.ok("✅ mensaje eliminado con éxito.");
    }
}
