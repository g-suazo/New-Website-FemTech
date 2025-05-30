package com.femtech.empresa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessRequestDTO {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String nombreEmpresa;
    private String sitioWebEmpresa;
    private String necesidadNegocio;

}
