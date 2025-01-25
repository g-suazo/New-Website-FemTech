package com.femtech.empresa.service;

import com.femtech.empresa.model.Appointment;
import com.femtech.empresa.model.AppointmentRequestDTO;
import com.femtech.empresa.enums.AppointmentStatus;
import com.femtech.empresa.repository.AppointmentRepository;

import java.util.List;

public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public void createAppointment(AppointmentRequestDTO request) {
        Appointment appointment = new Appointment();
        appointment.setClientName(request.clientName());
        appointment.setClientEmail(request.clientEmail());
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setStatus(AppointmentStatus.PENDING);
        repository.save(appointment);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return repository.findByStatus(status.name());
    }
}
