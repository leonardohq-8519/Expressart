package org.project.expressart.OpcionesComision.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionOptionsService {

    private final ModelMapper modelMapper;
    private final OpcionesComisionRepository commissionOptionsRepository;
    private final ComisionRepository commissionRepository;

    public List<CommissionOptionsResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return commissionOptionsRepository.findAllBy(pageable);
    }

    public CommissionOptionsResponseDTO findById (Long id) throws ResourceNotFoundException {
        OpcionesComision commOptions = commissionOptionsRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Commission option not found"));
        return modelMapper.map(commOptions, CommissionOptionsResponseDTO.class);
    }

    public List<CommissionOptionsResponseDTO> findByComisionId (Long commissionId) throws ResourceNotFoundException {
        List<OpcionesComision> commOptions = commissionOptionsRepository.findByComisionId(commissionId);
        if (commOptions.isEmpty()) {
            throw new ResourceNotFoundException("No commission options found for commission id: " + commissionId);
        }
        return commOptions.stream()
                .map(ticket -> modelMapper.map(ticket, CommissionOptionsResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CommissionOptionsResponseDTO create(CommissionOptionsRequestDTO request){
        OpcionesComision commOptions = new OpcionesComision();
        Comision commission = commissionRepository.findById(request.getComisionId())
                .orElseThrow(() -> new EntityNotFoundException("Commission not found"));
        commOptions.setComision(commission);
        commOptions.setNombre(request.getNombre());
        commOptions.setDescripcion(request.getDescripcion());
        commOptions.setPrecio(request.getPrecio());
        commOptions.setTiempoEntrega(request.getTiempoEntrega());
        commOptions.setNumeroRevisiones(request.getNumeroRevisiones());
        commOptions.setIncluyeArchivoFuente(request.getIncluyeArchivoFuente());
        commOptions.setEstaActiva(request.getEstaActiva());
        commissionOptionsRepository.save(commOptions);
        return modelMapper.map(commOptions, CommissionOptionsResponseDTO.class);
    }

    public CommissionOptionsResponseDTO update (Long id, CommissionOptionsRequestDTO request) throws ResourceNotFoundException {
        OpcionesComision updatedCommOptions = commissionOptionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commission options not found"));
        Comision commission = commissionRepository.findById(request.getComisionId())
                .orElseThrow(() -> new EntityNotFoundException("Commission not found"));

        updatedCommOptions.setComision(commission);
        if (request.getNombre() != null && !request.getNombre().isEmpty())
            updatedCommOptions.setNombre(request.getNombre());
        updatedCommOptions.setDescripcion(request.getDescripcion());
        if (request.getPrecio() != null)
            updatedCommOptions.setPrecio(request.getPrecio());
        if (request.getTiempoEntrega() != null)
            updatedCommOptions.setTiempoEntrega(request.getTiempoEntrega());
        if (request.getNumeroRevisiones() != null)
            updatedCommOptions.setNumeroRevisiones(request.getNumeroRevisiones());

        updatedCommOptions.setIncluyeArchivoFuente(request.getIncluyeArchivoFuente());
        updatedCommOptions.setEstaActiva(request.getEstaActiva());
        commissionOptionsRepository.save(updatedCommOptions);
        return modelMapper.map(updatedCommOptions, CommissionOptionsResponseDTO.class);
    }

    public void delete (Long id){
        if (commissionOptionsRepository.existsById(id))
            commissionOptionsRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission option with ID " + id + " doesn't exist");
    }
}