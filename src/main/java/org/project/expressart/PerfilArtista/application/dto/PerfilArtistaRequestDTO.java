package org.project.expressart.PerfilArtista.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PerfilArtistaRequestDTO {
    private Boolean comsDisponibles;
    private Integer tiempoEntregaPromedio;
    private List<Long> categoriaIds;
}