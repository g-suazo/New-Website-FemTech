package com.femtech.empresa;

import com.femtech.empresa.model.BusinessRequestDTO;
import com.femtech.empresa.service.EmailService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
public class FormularioTest {


    public static void main(String[] args) {

        // crea un contexto de Spring para cargar el servicio
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.femtech.empresa"); // escanea los paquetes de tu proyecto
        context.refresh();

        EmailService emailService = context.getBean(EmailService.class);

        Scanner scanner = new Scanner(System.in);
        BusinessRequestDTO request = new BusinessRequestDTO();

        System.out.println("¡Bienvenido al formulario de FemTech!");

        System.out.print("Nombre (obligatorio): ");
        request.setNombre(scanner.nextLine().trim());

        System.out.print("Apellido: ");
        request.setApellido(scanner.nextLine().trim());

        System.out.print("Correo electrónico (obligatorio): ");
        request.setCorreoElectronico(scanner.nextLine().trim());

        System.out.print("Nombre de la empresa: ");
        request.setNombreEmpresa(scanner.nextLine().trim());

        System.out.print("Sitio web de la empresa: ");
        request.setSitioWebEmpresa(scanner.nextLine().trim());

        System.out.print("Cuéntanos de tu necesidad de negocio (obligatorio): ");
        request.setNecesidadNegocio(scanner.nextLine().trim());

        // construye el correo a enviar
        String subject = "Nueva solicitud de contacto";
        String body = String.format(
                "Nombre: %s %s\nCorreo: %s\nEmpresa: %s\nSitio web: %s\nNecesidad: %s",
                request.getNombre(),
                request.getApellido(),
                request.getCorreoElectronico(),
                request.getNombreEmpresa(),
                request.getSitioWebEmpresa(),
                request.getNecesidadNegocio()
        );

        // envía el correo usando el servicio
        emailService.sendContactEmail("tu-correo-destino@example.com", subject, body);

        System.out.println("\n¡Gracias! Tu solicitud ha sido enviada.");
    }
}
