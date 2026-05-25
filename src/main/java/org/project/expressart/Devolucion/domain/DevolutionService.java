package org.project.expressart.Devolucion.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.infrastructure.DevolucionRepository;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.ResenaArtista.domain.ResenaArtista;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DevolutionService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final DevolucionRepository devolutionRepository;
    @Autowired
    private final OrdenRepository orderRepository;
    public List<DevolutionResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return devolutionRepository.findAllBy(pageable).stream()
                .map(d -> modelMapper.map(d, DevolutionResponseDTO.class))
                .collect(Collectors.toList());
    }
    public DevolutionResponseDTO  findById (Long id)throws ResourceNotFoundException {
        Devolucion devolution = devolutionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public DevolutionResponseDTO findByOrderId (Long orderId)throws ResourceNotFoundException{
        Devolucion devolution = devolutionRepository.findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public List<DevolutionResponseDTO> findByEstado (EstadoDevolucion estado)throws ResourceNotFoundException{
        List<Devolucion> artistReview = devolutionRepository.findByEstado(estado);
        if (artistReview.isEmpty()) {
            throw new ResourceNotFoundException("No devolutions found for status: " + estado);
        }
        return artistReview.stream()
                .map(ticket -> modelMapper.map(artistReview, DevolutionResponseDTO.class))
                .collect(Collectors.toList());
    }
    public DevolutionResponseDTO create(DevolutionRequestDTO request){
        Devolucion devolution = new Devolucion();
        Orden order = orderRepository.findById(request.getOrdenId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        devolution.setOrder(order);
        devolution.setMotivo(request.getMotivo());
        devolution.setMontoReembolso(request.getMontoReembolso());
        devolutionRepository.save(devolution);
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }
    public DevolutionResponseDTO  updateStatus (Long id, EstadoDevolucion estado)throws ResourceNotFoundException{
        Devolucion updatedDevolution = devolutionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Devolution not found"));
        updatedDevolution.setEstado(estado);
        devolutionRepository.save(updatedDevolution);
        return modelMapper.map(updatedDevolution, DevolutionResponseDTO.class);
    }
    public void delete (Long id){
        if (devolutionRepository.existsById(id))
            devolutionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Devolution with ID " + id + " doesn't exist");
    }

}