package org.project.expressart.Portafolio.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class PortafolioResponseDTO {
    private Long id;
    private Long perfilArtistaId;
    private String titulo;
    private String descripcion;
    private String portada_url;
    private Boolean esPublico;
    private ZonedDateTime fechaCreacion;
    private List<Long> postIds;
}