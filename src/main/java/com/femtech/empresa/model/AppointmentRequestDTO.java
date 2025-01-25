package com.femtech.empresa.model;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        String clientName,
        String clientEmail,
        LocalDateTime appointmentDate) {
}
