package org.project.expressart.NotificacionCorreo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.expressart.NotifiacionCorreo.domain.EstadoCorreo;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionCorreoEstadoDTO {
    private Long id;
    private EstadoCorreo estado;
    private String error;
    private ZonedDateTime fechaEnvio;
}