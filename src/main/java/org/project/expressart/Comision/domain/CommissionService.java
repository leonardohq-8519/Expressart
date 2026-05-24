package org.project.expressart.Comision.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final ComisionRepository commissionRepository;

    public List<CommissionResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Comision> comisiones = commissionRepository.findAll(pageable).getContent();
        return convertToDtoList(comisiones);
    }

    public CommissionResponseDTO findById(Long id) {
        Comision commission = commissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }

    public List<CommissionResponseDTO> findByPerfilArtistaId(Long artistaId) {
        List<Comision> comisiones = commissionRepository.findByPerfilArtistaId(artistaId);
        return convertToDtoList(comisiones);
    }

    public List<CommissionResponseDTO> findByPerfilArtistaIdAndEstaActiva(Long artistaId, Boolean status) {
        List<Comision> comisiones = commissionRepository.findByPerfilArtistaIdAndEstaActiva(artistaId, status);
        return convertToDtoList(comisiones);
    }

    public List<CommissionResponseDTO> findByCategoriaId(Long categoriaId) {
        List<Comision> comisiones = commissionRepository.findByCategoriaId(categoriaId);
        return convertToDtoList(comisiones);
    }

    public List<CommissionResponseDTO> findByTagsId(Long tagId) {
        List<Comision> comisiones = commissionRepository.findByTagsId(tagId);
        return convertToDtoList(comisiones);
    }

    public CommissionResponseDTO create(CommissionRequestDTO request) {
        Comision comision = modelMapper.map(request, Comision.class);
        Comision guardada = commissionRepository.save(comision);
        return modelMapper.map(guardada, CommissionResponseDTO.class);
    }

    public CommissionResponseDTO update(Long id, CommissionRequestDTO request) {
        if (!commissionRepository.existsById(id)) {
            throw new ResourceNotFoundEXception("Commission not found");
        }
        Comision comision = modelMapper.map(request, Comision.class);
        comision.setId(id);
        Comision actualizada = commissionRepository.save(comision);
        return modelMapper.map(actualizada, CommissionResponseDTO.class);
    }

    public void delete(Long id) {
        if (commissionRepository.existsById(id))
            commissionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission with ID " + id + " doesn't exist");
    }

    private List<CommissionResponseDTO> convertToDtoList(List<Comision> comisiones) {
        return comisiones.stream()
                .map(comision -> modelMapper.map(comision, CommissionResponseDTO.class))
                .collect(Collectors.toList());
    }
}