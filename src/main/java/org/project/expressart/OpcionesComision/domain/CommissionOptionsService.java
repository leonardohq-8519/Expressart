package org.project.expressart.OpcionesComision.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    }
    public CommissionOptionsResponseDTO  update (Long id, CommissionOptionsRequestDTO request){
    }
    public void delete (Long id){
        if (commissionOptionsRepository.existsById(id))
            commissionOptionsRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission option with ID " + id + " doesn't exist");
    }

}