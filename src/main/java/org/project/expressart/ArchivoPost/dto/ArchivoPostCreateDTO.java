package org.project.expressart.ArchivoPost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPostCreateDTO {
    private Long postId;
    private String url;
    private Integer ordenVisualizacion;
}