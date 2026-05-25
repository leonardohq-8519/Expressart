package org.project.expressart.ResenaArtista.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.ResenaArtista.infrastructure.ResenaArtistaRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exception.ResourceNotFoundException;
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
    private final OrdenRepository orderRepository;
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ArtistReviewResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return artistReviewRepository.findAllBy(pageable);
    }
    public ArtistReviewResponseDTO  findById (Long id) throws ResourceNotFoundException {
        ResenaArtista artistReview = artistReviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Artist review not found"));
        return modelMapper.map(artistReview, ArtistReviewResponseDTO.class);
    }
    public List<ArtistReviewResponseDTO> findByArtistaId (Long artistId) throws ResourceNotFoundException {
        List<ResenaArtista> artistReview = artistReviewRepository.findByArtistaId(artistId);
        if (artistReview.isEmpty()) {
            throw new ResourceNotFoundException("No artists reviews found for artist id: " + artistId);
        }
        return artistReview.stream()
                .map(ticket -> modelMapper.map(artistReview, ArtistReviewResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<ArtistReviewResponseDTO> findByClienteId (Long clientId) throws ResourceNotFoundException {
        List<ResenaArtista> artistReview = artistReviewRepository.findByClienteId(clientId);
        if (artistReview.isEmpty()) {
            throw new ResourceNotFoundException("No artists reviews found for client id: " + clientId);
        }
        return artistReview.stream()
                .map(ticket -> modelMapper.map(artistReview, ArtistReviewResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ArtistReviewResponseDTO create(ArtistReviewRequestDTO request){
        ResenaArtista artistReview = new ResenaArtista();
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        artistReview.setOrder(order);
        Usuario artist = userRepository.findById(request.getArtistaId()).orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        artistReview.setArtist(artist);
        Usuario client = userRepository.findById(request.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        artistReview.setClient(client);
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
