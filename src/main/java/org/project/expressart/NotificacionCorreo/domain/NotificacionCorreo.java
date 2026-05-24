package org.project.expressart.NotificacionCorreo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.Usuario.domain.Usuario;

import java.time.ZonedDateTime;

@Entity
@Table(name = "notificaciones_correo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "destinatario_email", nullable = false, length = 255)
    private String destinatarioEmail;

    @Column(nullable = false, length = 255)
    private String asunto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoCorreo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCorreo estado = EstadoCorreo.PENDIENTE;

    @Column(columnDefinition = "TEXT")
    private String error;

    @Column(nullable = false)
    private Integer intentos = 0;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private ZonedDateTime fechaCreacion;

    @Column(name = "fecha_envio")
    private ZonedDateTime fechaEnvio;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now();
    }
}
