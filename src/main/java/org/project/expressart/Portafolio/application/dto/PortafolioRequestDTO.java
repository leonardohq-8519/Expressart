package org.project.expressart.Portafolio.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortafolioRequestDTO {
    private Long perfilArtistaId;
    private String titulo;
    private String descripcion;
    private String portada_url;
    private Boolean esPublico;
}