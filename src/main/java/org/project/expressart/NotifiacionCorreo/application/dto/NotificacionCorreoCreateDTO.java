package org.project.expressart.NotifiacionCorreo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.NotifiacionCorreo.domain.TipoCorreo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCorreoCreateDTO {
    private Long usuarioId;
    private String destinatarioEmail;
    private String asunto;
    private TipoCorreo tipo;
}