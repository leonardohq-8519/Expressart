package org.project.expressart.ResenaArtista.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.ResenaArtista.infrastructure.ResenaArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistReviewService {
    @Autowired
    private final ResenaArtistaRepository artistReviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ArtistReviewResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return artistReviewRepository.findAllBy(pageable);
    }
    public ArtistReviewResponseDTO  findById (Long id){
        ResenaArtista artistReview = artistReviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Artist review not found"));
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }
    public ArtistReviewResponseDTO findByArtistaId (Long artistId){
        ResenaArtista artistReview = artistReviewRepository.findByArtistaId(artistId).orElseThrow(()-> new ResourceNotFoundEXception("Artist review not found"));
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }
    public ArtistReviewResponseDTO findByClienteId (Long clientId){
        ResenaArtista artistReview = artistReviewRepository.findByClienteId(clientId).orElseThrow(()-> new ResourceNotFoundEXception("Artist review not found"));
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }

    public ArtistReviewResponseDTO create(ArtistReviewRequestDTO request){
        ResenaArtista artistReview = new ResenaArtista();
        artistReview.setScore(request.getPuntuacion());
        artistReview.setComment(request.getComentario());
        artistReviewRepository.save(artistReview);
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }
    public void delete (Long id){
        if (artistReviewRepository.existsById(id))
            artistReviewRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist review with ID " + id + " doesn't exist");
    }
}
