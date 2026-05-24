package org.project.expressart.ArchivoPost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPostResponseDTO {
    private Long id;
    private String url;
    private Integer ordenVisualizacion;
}