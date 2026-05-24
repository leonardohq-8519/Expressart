package org.project.expressart.ResenaCliente.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.project.expressart.ResenaCliente.infrastructure.ResenaClienteRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientReviewService {

    @Autowired
    private final ResenaClienteRepository cliReviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ClientReviewResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ResenaCliente> reviews = cliReviewRepository.findAll(pageable).getContent();
        return convertToDtoList(reviews);
    }

    public ClientReviewResponseDTO findById(Long id) {
        ResenaCliente cliReview = cliReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Client review not found"));
        return modelMapper.map(cliReview, ClientReviewResponseDTO.class);
    }

    public List<ClientReviewResponseDTO> findByClienteId(Long clientId) {
        List<ResenaCliente> reviews = cliReviewRepository.findByClienteId(clientId);
        return convertToDtoList(reviews);
    }

    public List<ClientReviewResponseDTO> findByArtistaId(Long artistId) {
        List<ResenaCliente> reviews = cliReviewRepository.findByArtistaId(artistId);
        return convertToDtoList(reviews);
    }

    public ClientReviewResponseDTO create(ClientReviewRequestDTO request) {
        ResenaCliente cliReview = modelMapper.map(request, ResenaCliente.class);
        ResenaCliente savedReview = cliReviewRepository.save(cliReview);
        return modelMapper.map(savedReview, ClientReviewResponseDTO.class);
    }

    public void delete(Long id) {
        if (cliReviewRepository.existsById(id))
            cliReviewRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Client review with ID " + id + " doesn't exist");
    }

    private List<ClientReviewResponseDTO> convertToDtoList(List<ResenaCliente> reviews) {
        return reviews.stream()
                .map(review -> modelMapper.map(review, ClientReviewResponseDTO.class))
                .collect(Collectors.toList());
    }
}