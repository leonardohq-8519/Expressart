package org.project.expressart.Comision.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.expressart.OpcionesComision.dto.OpcionesComisionResponseDTO;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class ComisionResponseDTO {
    private Long id;
    private Long perfilArtistaId;
    private String nombreArtista;
    private String titulo;
    private String descripcion;
    private String portadaUrl;
    private Boolean estaActiva;
    private ZonedDateTime fechaCreacion;
    private List<String> categorias;
    private List<String> tags;
    private List<String> imagenesUrls;
    private List<OpcionesComisionResponseDTO> opciones;
}