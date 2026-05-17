package org.project.expressart.Post.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long portafolio_id;

    private Long tag_id;

    private String titulo;

    private String cuerpo;

    private ZonedDateTime fecha_publicacion;
    /*
    id post?
    id artista
    titulo
    cuerpo
    archivos
    fecha publicacion
    likes
    tags usados
     */
}