package org.project.expressart.NotificacionCorreo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.TipoCorreo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCorreoResponseDTO {
    private Long id;
    private String destinatarioEmail;
    private String asunto;
    private TipoCorreo tipo;
    private EstadoCorreo estado;
    private Integer intentos;
}