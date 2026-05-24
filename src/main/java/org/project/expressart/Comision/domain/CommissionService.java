package org.project.expressart.Comision.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ComisionRepository commissionRepository;
    public List<CommissionResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return commissionRepository.findAllBy(pageable);
    }
    public CommissionResponseDTO  findById (Long id){
        Comision commission = commissionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByPerfilArtistaId (Long artistaId){
        Comision commission = commissionRepository.findByPerfilArtistaId(artistaId).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByPerfilArtistaIdAndEstaActiva (Long artistaId, Boolean status){
        Comision commission = commissionRepository.findByPerfilArtistaIdAndEstaActiva(artistaId, status).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByTagsId (Long tagId){
        Comision commission = commissionRepository.findByTagsId(tagId).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO create(CommissionRequestDTO request){
    }
    public CommissionResponseDTO  update (Long id, CommissionRequestDTO request){
    }
    public void delete (Long id){
        if (commissionRepository.existsById(id))
            commissionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission with ID " + id + " doesn't exist");
    }

}