package org.project.expressart.ResenaArtista.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResenaArtistaRequestDTO {
    private Long ordenId;
    private Long clienteId;
    private Long artistaId;
    private Short puntuacion;
    private String comentario;
}