package org.project.expressart.Devolucion.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.infrastructure.DevolucionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DevolutionService{
    private final DevolucionRepository devolutionRepository;
    public List<DevolutionResponseDTO> findAll(){
    }
    public DevolutionResponseDTO  findById (Long id){
    }
    public DevolutionResponseDTO findByOrderId (Long orderId){
    }
    public DevolutionResponseDTO findByEstado (EstadoDevolucion estado){
    }
    public DevolutionResponseDTO create(DevolutionRequestDTO request){
    }
    public DevolutionResponseDTO  updateStatus (Long id, EstadoDevolucion estado){
    }
    public void delete (Long id){
    }

}