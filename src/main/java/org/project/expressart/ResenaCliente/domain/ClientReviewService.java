package org.project.expressart.ResenaCliente.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.project.expressart.ResenaCliente.infrastructure.ResenaClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
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
    private final OrdenRepository orderRepository;
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ClientReviewResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return cliReviewRepository.findAllBy(pageable);
    }
    public ClientReviewResponseDTO  findById (Long id)throws ResourceNotFoundException{
        ResenaCliente cliReview = cliReviewRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Client review not found"));
        return modelMapper.map(cliReview, ClientReviewResponseDTO.class);
    }
    public List<ClientReviewResponseDTO> findByClienteId (Long clientId)throws ResourceNotFoundException{
        List<ResenaCliente> cliReview = cliReviewRepository.findByClienteId(clientId);
        if (cliReview.isEmpty()) {
            throw new ResourceNotFoundException("No client reviews found for client id: " + clientId);
        }
        return cliReview.stream()
                .map(ticket -> modelMapper.map(cliReview, ClientReviewResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<ClientReviewResponseDTO> findByArtistaId (Long artistId)throws ResourceNotFoundException {
        List<ResenaCliente> cliReview = cliReviewRepository.findByArtistaId(artistId);
        if (cliReview.isEmpty()) {
            throw new ResourceNotFoundException("No client reviews found for artist id: " + artistId);
        }
        return cliReview.stream()
                .map(ticket -> modelMapper.map(cliReview, ClientReviewResponseDTO.class))
                .collect(Collectors.toList());
    }
    public ClientReviewResponseDTO create(ClientReviewRequestDTO request){
        ResenaCliente clientReview = new ResenaCliente();
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        clientReview.setOrden(order);
        Usuario artist = userRepository.findById(request.getArtistaId()).orElseThrow(() -> new EntityNotFoundException("Artist not found"));
        clientReview.setArtista(artist);
        Usuario client = userRepository.findById(request.getClienteId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        clientReview.setCliente(client);
        clientReview.setPuntuacion(request.getPuntuacion());
        clientReview.setComentario(request.getComentario());
        cliReviewRepository.save(clientReview);
        return modelMapper.map(clientReview, ClientReviewResponseDTO.class);
    }
    public void delete (Long id){
        if (cliReviewRepository.existsById(id))
            cliReviewRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Client review with ID " + id + " doesn't exist");
    }
}

