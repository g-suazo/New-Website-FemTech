package com.femtech.empresa.controller;

import com.femtech.empresa.model.AppointmentRequestDTO;
import com.femtech.empresa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDTO request) {
        service.createAppointment(request);
        return ResponseEntity.ok("Cita creada exitósamente!");
    }
}
