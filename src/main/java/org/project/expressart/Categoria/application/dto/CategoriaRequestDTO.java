package org.project.expressart.Categoria.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequestDTO {
    private String nombre;
    private String descripcion;
    private String iconoUrl;
}