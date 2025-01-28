package com.femtech.empresa.service;

import com.femtech.empresa.model.BusinessRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    public void processRequest(BusinessRequestDTO request) {
        // aquí puedes guardar la información en la base de datos o procesarla
        System.out.println("Procesando solicitud:");
        System.out.println("Nombre completo: " + request.getNombre() + " " + request.getApellido());
        System.out.println("Correo electrónico: " + request.getCorreoElectronico());
        System.out.println("Empresa: " + request.getNombreEmpresa());
        System.out.println("Sitio web: " + request.getSitioWebEmpresa());
        System.out.println("Necesidad: " + request.getNecesidadNegocio());
    }
}
