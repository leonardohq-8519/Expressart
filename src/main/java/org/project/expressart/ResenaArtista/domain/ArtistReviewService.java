package org.project.expressart.ResenaArtista.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistReviewService {
    public List<ArtistReviewResponseDTO> findAll(){
    }
    public ArtistReviewResponseDTO  findById (Long id){
    }
    public ArtistReviewResponseDTO findByArtistaId (Long artistId){
    }
    public ArtistReviewResponseDTO findByClienteId (Long clientId){
    }

    public ArtistReviewResponseDTO create(ArtistReviewRequestDTO request){
    }
    public void delete (Long id){
    }
}
