package org.project.expressart.ResenaArtista.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;


@Getter
@Setter
public class ResenaArtistaResponseDTO {
    private Long id;
    private Long ordenId;
    private Long clienteId;
    private String nombreCliente;
    private Long artistaId;
    private String nombreArtista;
    private Short puntuacion;
    private String comentario;
    private ZonedDateTime fechaCreacion;
}