package org.project.expressart.PerfilArtista.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ArtistProfileRequestDTO {
    private Boolean comsDisponibles;
    private Integer tiempoEntregaPromedio;
    private List<Long> categoriaIds;
}