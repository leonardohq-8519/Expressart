package org.project.expressart.Devolucion.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.infrastructure.DevolucionRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DevolutionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final DevolucionRepository devolutionRepository;

    public List<DevolutionResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Devolucion> devoluciones = devolutionRepository.findAll(pageable).getContent();
        return convertToDtoList(devoluciones);
    }

    public DevolutionResponseDTO findById(Long id) {
        Devolucion devolution = devolutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }

    public DevolutionResponseDTO findByOrderId(Long orderId) {
        Devolucion devolution = devolutionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundEXception("Devolution not found"));
        return modelMapper.map(devolution, DevolutionResponseDTO.class);
    }

    public List<DevolutionResponseDTO> findByEstado(EstadoDevolucion estado) {
        List<Devolucion> devoluciones = devolutionRepository.findByEstado(estado);
        return convertToDtoList(devoluciones);
    }

    public DevolutionResponseDTO create(DevolutionRequestDTO request) {
        Devolucion devolution = modelMapper.map(request, Devolucion.class);
        Devolucion savedDevolution = devolutionRepository.save(devolution);
        return modelMapper.map(savedDevolution, DevolutionResponseDTO.class);
    }

    public DevolutionResponseDTO updateStatus(Long id, EstadoDevolucion estado) {
        Devolucion existingDevolution = devolutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Devolution not found"));

        existingDevolution.setEstado(estado);

        Devolucion updatedDevolution = devolutionRepository.save(existingDevolution);
        return modelMapper.map(updatedDevolution, DevolutionResponseDTO.class);
    }

    public void delete(Long id) {
        if (devolutionRepository.existsById(id))
            devolutionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Devolution with ID " + id + " doesn't exist");
    }

    private List<DevolutionResponseDTO> convertToDtoList(List<Devolucion> devoluciones) {
        return devoluciones.stream()
                .map(devolucion -> modelMapper.map(devolucion, DevolutionResponseDTO.class))
                .collect(Collectors.toList());
    }
}