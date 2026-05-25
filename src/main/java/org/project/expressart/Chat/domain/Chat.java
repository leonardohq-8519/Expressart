package org.project.expressart.Chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Mensaje.domain.Mensaje;
import org.project.expressart.Orden.domain.Orden;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes = new ArrayList<>();

    @Column(name = "canal_redis", nullable = false, unique = true, length = 100)
    private String canalRedis;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
        this.canalRedis = "chat:" + this.orden.getId();
    }

}