package org.project.expressart.PerfilArtista.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistProfileService {
    private final PerfilArtistaRepository artistProfileRepository;
    public List<ArtistProfileResponseDTO> findAll(){
    }
    public ArtistProfileResponseDTO  findById (Long id){
    }
    public ArtistProfileResponseDTO findByUsuarioId (Long userId){
    }
    public ArtistProfileResponseDTO create(ArtistProfileRequestDTO request){
    }
    public ArtistProfileResponseDTO  update (Long id, ArtistProfileRequestDTO request){
    }
    public void delete (Long id){
    }

}
