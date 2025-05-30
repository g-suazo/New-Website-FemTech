package com.femtech.empresa.model;

public record MessageRequestDTO(
        String clientName,
        String clientEmail,
        String subject,
        String content) {
}
