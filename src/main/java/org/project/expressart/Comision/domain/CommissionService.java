package org.project.expressart.Comision.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionService{
    private final ComisionRepository commissionRepository;
    public List<CommissionResponseDTO> findAll(){
    }
    public CommissionResponseDTO  findById (Long id){
    }
    public CommissionResponseDTO findByPerfilArtistaId (Long artistaId){
    }
    public CommissionResponseDTO findByPerfilArtistaIdAndEstaActiva (Long artistaId, Bool estado){
    }
    public CommissionResponseDTO findByTagsId (Long tagId){
    }
    public CommissionResponseDTO create(CommissionRequestDTO request){
    }
    public CommissionResponseDTO  update (Long id, CommissionRequestDTO request){
    }
    public void delete (Long id){
    }

}