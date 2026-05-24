package org.project.expressart.ImagenPost.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenPostCreateDTO {
    private Long postId;
    private String url;
    private Integer orden;
}