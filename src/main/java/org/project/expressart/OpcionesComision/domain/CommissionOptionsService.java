package org.project.expressart.OpcionesComision.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionOptionsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final OpcionesComisionRepository commissionOptionsRepository;

    public List<CommissionOptionsResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<OpcionesComision> opciones = commissionOptionsRepository.findAll(pageable).getContent();
        return convertToDtoList(opciones);
    }

    public CommissionOptionsResponseDTO findById(Long id) {
        OpcionesComision commOptions = commissionOptionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Commission option not found"));
        return modelMapper.map(commOptions, CommissionOptionsResponseDTO.class);
    }

    public List<CommissionOptionsResponseDTO> findByComisionId(Long commissionId) {
        List<OpcionesComision> opciones = commissionOptionsRepository.findByComisionId(commissionId);
        return convertToDtoList(opciones);
    }

    public CommissionOptionsResponseDTO create(CommissionOptionsRequestDTO request) {
        OpcionesComision commOptions = modelMapper.map(request, OpcionesComision.class);
        OpcionesComision savedOptions = commissionOptionsRepository.save(commOptions);
        return modelMapper.map(savedOptions, CommissionOptionsResponseDTO.class);
    }

    public CommissionOptionsResponseDTO update(Long id, CommissionOptionsRequestDTO request) {
        OpcionesComision existingOptions = commissionOptionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Commission option not found"));

        modelMapper.map(request, existingOptions);
        existingOptions.setId(id);

        OpcionesComision updatedOptions = commissionOptionsRepository.save(existingOptions);
        return modelMapper.map(updatedOptions, CommissionOptionsResponseDTO.class);
    }

    public void delete(Long id) {
        if (commissionOptionsRepository.existsById(id))
            commissionOptionsRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission option with ID " + id + " doesn't exist");
    }

    private List<CommissionOptionsResponseDTO> convertToDtoList(List<OpcionesComision> opciones) {
        return opciones.stream()
                .map(opcion -> modelMapper.map(opcion, CommissionOptionsResponseDTO.class))
                .collect(Collectors.toList());
    }
}