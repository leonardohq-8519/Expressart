package org.project.expressart.Post.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.ImagenPost.domain.ImagenPost;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Tags.domain.Tags;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portafolio_id", nullable = false)
    private Portafolio portafolio;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "es_publico", nullable = false)
    private Boolean esPublico = true;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private ZonedDateTime fechaPublicacion;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenPost> imagenes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_categoria",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tags> tags = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.fechaPublicacion = ZonedDateTime.now();
    }
}