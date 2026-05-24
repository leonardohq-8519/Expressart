package org.project.expressart.Comision.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CommissionRequestDTO {
    private Long perfilArtistaId;
    private String titulo;
    private String descripcion;
    private String portadaUrl;
    private Boolean estaActiva;
    private List<Long> categoriaIds;
    private List<Long> tagIds;
}