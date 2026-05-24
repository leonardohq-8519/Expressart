package org.project.expressart.ImagenComision.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenComisionResponseDTO {
    private Long id;
    private String url;
    private Integer orden;
}