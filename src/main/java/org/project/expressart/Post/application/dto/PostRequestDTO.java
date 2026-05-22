package org.project.expressart.Post.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PostRequestDTO {
    private Long portafolioId;
    private String titulo;
    private String descripcion;
    private Boolean esPublico;
    private List<Long> categoriaIds;
    private List<Long> tagIds;

}