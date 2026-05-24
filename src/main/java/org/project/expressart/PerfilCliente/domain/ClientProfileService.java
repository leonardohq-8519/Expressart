package org.project.expressart.PerfilCliente.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilCliente.dto.ClientProfileRequestDTO;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientProfileService {
    @Autowired
    private final PerfilClienteRepository clientProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ClientProfileResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return clientProfileRepository.findAllBy(pageable);
    }
    public ClientProfileResponseDTO  findById (Long id){
        PerfilCliente clientProfile = clientProfileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Client profile not found"));
        return modelMapper.map(clientProfile, ClientProfileResponseDTO.class);
    }
    public ClientProfileResponseDTO findByUsuarioId (Long userId){
        PerfilCliente clientProfile = clientProfileRepository.findByUsuarioId(userId).orElseThrow(()-> new ResourceNotFoundEXception("Client profile not found"));
        return modelMapper.map(clientProfile, ClientProfileResponseDTO.class);
    }
    public ClientProfileResponseDTO create(ClientProfileRequestDTO request){
    }
    public ClientProfileResponseDTO  update (Long id, ClientProfileRequestDTO request){
    }
    public void delete (Long id){
        if (clientProfileRepository.existsById(id))
            clientProfileRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Client profile with ID " + id + " doesn't exist");
    }

}
