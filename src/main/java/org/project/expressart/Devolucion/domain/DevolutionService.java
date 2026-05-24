package org.project.expressart.Devolucion.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.infrastructure.DevolucionRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DevolutionService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final DevolucionRepository devolutionRepository;
    public List<DevolutionResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return devolutionRepository.findAllBy(pageable);
    }
    public DevolutionResponseDTO  findById (Long id){
        Devolucion devolution = devolutionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public DevolutionResponseDTO findByOrderId (Long orderId){
        Devolucion devolution = devolutionRepository.findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundEXception("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public DevolutionResponseDTO findByEstado (EstadoDevolucion estado){
        Devolucion devolution = devolutionRepository.findByEstado(estado).orElseThrow(()-> new ResourceNotFoundEXception("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public DevolutionResponseDTO create(DevolutionRequestDTO request){
    }
    public DevolutionResponseDTO  updateStatus (Long id, EstadoDevolucion estado){
    }
    public void delete (Long id){
        if (devolutionRepository.existsById(id))
            devolutionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Devolution with ID " + id + " doesn't exist");
    }

}