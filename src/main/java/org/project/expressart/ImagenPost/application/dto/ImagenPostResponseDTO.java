package org.project.expressart.ImagenPost.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenPostResponseDTO {
    private Long id;
    private String url;
    private Integer orden;
}