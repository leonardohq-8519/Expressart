package org.project.expressart.PerfilCliente.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.PerfilCliente.dto.ClientProfileRequestDTO;
import org.project.expressart.PerfilCliente.dto.ClientProfileResponseDTO;
import org.project.expressart.PerfilCliente.infrastructure.PerfilClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientProfileService {

    private final PerfilClienteRepository clientProfileRepository;
    public List<ClientProfileResponseDTO> findAll(){
    }
    public ClientProfileResponseDTO  findById (Long id){
    }
    public ClientProfileResponseDTO findByUsuarioId (Long userId){
    }
    public ClientProfileResponseDTO create(ClientProfileRequestDTO request){
    }
    public ClientProfileResponseDTO  update (Long id, ClientProfileRequestDTO request){
    }
    public void delete (Long id){
    }

}
