package org.project.expressart.OpcionesComision.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionOptionsService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final OpcionesComisionRepository commissionOptionsRepository;
    @Autowired
    private final ComisionRepository commissionRepository;
    public List<CommissionOptionsResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return commissionOptionsRepository.findAllBy(pageable);
    }
    public CommissionOptionsResponseDTO  findById (Long id){
        OpcionesComision commOptions = commissionOptionsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Commission option not found"));
        return modelMapper.map(commOptions, CommissionOptionsResponseDTO.class);
    }
    public CommissionOptionsResponseDTO findByComisionId (Long commissionId){
        OpcionesComision commOptions = commissionOptionsRepository.findByComision(commissionId).orElseThrow(()-> new ResourceNotFoundEXception("Commission option not found"));
        return modelMapper.map(commOptions, CommissionOptionsResponseDTO.class);
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
    public CommissionOptionsResponseDTO  update (Long id, CommissionOptionsRequestDTO request){
        OpcionesComision updatedCommOptions = commissionOptionsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commission options not found"));
        Comision commission = commissionRepository.findById(request.getComisionId())
                .orElseThrow(() -> new EntityNotFoundException("Commission not found"));
        updatedCommOptions.setComision(commission);
        if (request.getNombre()!= null && !request.getNombre().isEmpty())
            updatedCommOptions.setNombre(request.getNombre());
        updatedCommOptions.setDescripcion(request.getDescripcion());
        if (request.getPrecio()!= null)
            updatedCommOptions.setPrecio(request.getPrecio());
        if (request.getTiempoEntrega()!= null)
            updatedCommOptions.setTiempoEntrega(request.getTiempoEntrega());
        if (request.getNumeroRevisiones()!= null)
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