package org.project.expressart.ResenaArtista.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistReviewRequestDTO {
    private Long ordenId;
    private Long clienteId;
    private Long artistaId;
    private Short puntuacion;
    private String comentario;
}