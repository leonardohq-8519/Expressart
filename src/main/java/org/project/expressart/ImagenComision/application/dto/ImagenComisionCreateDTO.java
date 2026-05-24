package org.project.expressart.ImagenComision.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenComisionCreateDTO {
    private Long comisionId;
    private String url;
    private Integer orden;
}