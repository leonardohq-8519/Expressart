package org.project.expressart.ResenaArtista.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.ResenaArtista.infrastructure.ResenaArtistaRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistReviewService {

    @Autowired
    private final ResenaArtistaRepository artistReviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ArtistReviewResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ResenaArtista> reviews = artistReviewRepository.findAll(pageable).getContent();
        return convertToDtoList(reviews);
    }

    public ArtistReviewResponseDTO findById(Long id) {
        ResenaArtista artistReview = artistReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Artist review not found"));
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }

    public List<ArtistReviewResponseDTO> findByArtistaId(Long artistId) {
        List<ResenaArtista> reviews = artistReviewRepository.findByArtistaId(artistId);
        return convertToDtoList(reviews);
    }

    public List<ArtistReviewResponseDTO> findByClienteId(Long clientId) {
        List<ResenaArtista> reviews = artistReviewRepository.findByClienteId(clientId);
        return convertToDtoList(reviews);
    }

    public ArtistReviewResponseDTO create(ArtistReviewRequestDTO request) {
        ResenaArtista artistReview = new ResenaArtista();
        artistReview.setScore(request.getPuntuacion());
        artistReview.setComment(request.getComentario());
        
        ResenaArtista savedReview = artistReviewRepository.save(artistReview);
        return modelMapper.map(savedReview, ArtistReviewResponseDTO.class);
    }

    public void delete(Long id) {
        if (artistReviewRepository.existsById(id))
            artistReviewRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist review with ID " + id + " doesn't exist");
    }

    private List<ArtistReviewResponseDTO> convertToDtoList(List<ResenaArtista> reviews) {
        return reviews.stream()
                .map(review -> modelMapper.map(review, ArtistReviewResponseDTO.class))
                .collect(Collectors.toList());
    }
}