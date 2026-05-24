package org.project.expressart.ResenaCliente.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.project.expressart.ResenaCliente.infrastructure.ResenaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientReviewService {
    @Autowired
    private final ResenaClienteRepository cliReviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ClientReviewResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return cliReviewRepository.findAllBy(pageable);
    }
    public ClientReviewResponseDTO  findById (Long id){
        ResenaCliente cliReview = cliReviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Client review not found"));
        return modelMapper.map(cliReview, ClientReviewResponseDTO.class);
    }
    public ClientReviewResponseDTO findByClienteId (Long clientId){
        ResenaCliente cliReview = cliReviewRepository.findByClienteId(clientId).orElseThrow(()-> new ResourceNotFoundEXception("Client review not found"));
        return modelMapper.map(cliReview, ClientReviewResponseDTO.class);
    }
    public ClientReviewResponseDTO findByArtistaId (Long artistId){
        ResenaCliente cliReview = cliReviewRepository.findByArtistaId(artistId).orElseThrow(()-> new ResourceNotFoundEXception("Client review not found"));
        return modelMapper.map(cliReview, ClientReviewResponseDTO.class);
    }
    public ClientReviewResponseDTO create(ClientReviewRequestDTO request){
    }
    public void delete (Long id){
        if (cliReviewRepository.existsById(id))
            cliReviewRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Client review with ID " + id + " doesn't exist");
    }
}

