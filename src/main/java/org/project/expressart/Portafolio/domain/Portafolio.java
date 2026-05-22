package org.project.expressart.Portafolio.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.Post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "portafolio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Portafolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_artista_id", nullable = false)
    private PerfilArtista perfilArtista;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "portada_url", length = 500)
    private String portada_url;

    @Column(name = "es_publico", nullable = false)
    private Boolean esPublico = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    // 1 a N con los posts
    @OneToMany(mappedBy = "portafolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

}