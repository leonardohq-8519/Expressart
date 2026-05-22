package org.project.expressart.Post.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponseDTO {
    private Long id;
    private Long portafolioId;
    private String titulo;
    private String descripcion;
    private Boolean esPublico;
    private ZonedDateTime fechaPublicacion;
    private List<String> categorias;
    private List<String> tags;
    private List<String> imagenesUrls;
}