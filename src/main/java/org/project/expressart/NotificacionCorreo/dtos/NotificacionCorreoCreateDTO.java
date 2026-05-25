package org.project.expressart.NotificacionCorreo.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.NotificacionCorreo.domain.TipoCorreo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCorreoCreateDTO {
    private Long usuarioId;
    private String destinatarioEmail;
    private String asunto;
    private String mensaje;
    private TipoCorreo tipo;
}