package org.project.expressart.Mensaje.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Chat.domain.Chat;
import org.project.expressart.Usuario.domain.Usuario;

import java.time.ZonedDateTime;


@Entity
@Table(name = "mensaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario remitente;

    @Column(name = "archivo_url", length = 500)
    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_archivo", length = 20)
    private TipoArchivo tipoArchivo;

    @Column(nullable = false)
    private Boolean leido = false;

    @Column(nullable = false)
    private Boolean persistido = false;

    @Column(name = "fecha_envio", nullable = false, updatable = false)
    private ZonedDateTime fechaEnvio;

    @Column(name = "fecha_lectura")
    private ZonedDateTime fechaLectura;

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