package org.project.expressart.ArchivoPost.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ArchivoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long post_id;

    private String url;

    private Integer orden_visualizacion;
}
