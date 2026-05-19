package org.project.expressart.Mensaje.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Usuario.domain.Usuario;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Entity
@Table(name = "mensaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario remitente;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaEnvio;

    @PrePersist
    protected void onCreate(){
        this.fechaEnvio = ZonedDateTime.now();
    }

    /*
    id del mensaje
    id del chat
    id del que envia
    id del receptor
    string texto
    archivos
    fueleido?
    fecha de envio
    entregafinal?
     */
}