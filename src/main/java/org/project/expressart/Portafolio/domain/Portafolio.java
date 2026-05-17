package org.project.expressart.Portafolio.domain;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.project.expressart.Post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;


@Getter
@Setter
public class Portafolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long artista_id;

    private String titulo;

    private String descripcion;

    private String portada_url;

    private ZonedDateTime fecha_creacion;

    // 1 a N con los posts
    private List<Post> posts;

}