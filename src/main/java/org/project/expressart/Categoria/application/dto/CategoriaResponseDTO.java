package org.project.expressart.Categoria.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String iconoUrl;
}