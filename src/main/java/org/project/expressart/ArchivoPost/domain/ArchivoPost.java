package org.project.expressart.ArchivoPost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Post.domain.Post;

@Entity
@Table(name = "archivos_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "orden_visualizacion")
    private Integer ordenVisualizacion;
}