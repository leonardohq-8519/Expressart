package org.project.expressart.PerfilCliente.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilCliente.dto.ClientProfileRequestDTO;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientProfileService {

    private final PerfilClienteRepository clientProfileRepository;
    private final UsuarioRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ClientProfileResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return clientProfileRepository.findAllBy(pageable);
    }

    public ClientProfileResponseDTO findById (Long id) throws ResourceNotFoundException {
        PerfilCliente clientProfile = clientProfileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Client profile not found"));
        return modelMapper.map(clientProfile, ClientProfileResponseDTO.class);
    }

    public ClientProfileResponseDTO findByUsuarioId (Long userId) throws ResourceNotFoundException {
        PerfilCliente clientProfile = clientProfileRepository.findByUsuarioId(userId).orElseThrow(()-> new ResourceNotFoundException("Client profile not found"));
        return modelMapper.map(clientProfile, ClientProfileResponseDTO.class);
    }

    public ClientProfileResponseDTO create(Long userId){
        PerfilCliente clientProfile = new PerfilCliente();
        Usuario user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        clientProfile.setUsuario(user);
        clientProfileRepository.save(clientProfile);
        return modelMapper.map(clientProfile, ClientProfileResponseDTO.class);
    }

    public ClientProfileResponseDTO update (Long id, ClientProfileRequestDTO request) throws ResourceNotFoundException {
        PerfilCliente updClientProfile = clientProfileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client profile not found"));
        Usuario user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        updClientProfile.setUsuario(user);
        updClientProfile.setRatingPromedio(request.getRatingPromedio());
        updClientProfile.setTotalResenas(request.getTotalResenas());
        updClientProfile.setOrdenesRealizadas(request.getOrdenesRealizadas());
        clientProfileRepository.save(updClientProfile);
        return modelMapper.map(updClientProfile, ClientProfileResponseDTO.class);
    }

    public void delete (Long id){
        if (clientProfileRepository.existsById(id))
            clientProfileRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Client profile with ID " + id + " doesn't exist");
    }
}