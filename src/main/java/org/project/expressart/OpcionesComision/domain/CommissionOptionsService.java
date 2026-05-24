package org.project.expressart.OpcionesComision.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionOptionsService{
    private final OpcionesComisionRepository comisionOptionsRepository;
    public List<CommissionOptionsResponseDTO> findAll(){
    }
    public CommissionOptionsResponseDTO  findById (Long id){
    }
    public CommissionOptionsResponseDTO findByComisionId (Long comisionId){
    }
    public CommissionOptionsResponseDTO create(CommissionOptionsRequestDTO request){
    }
    public CommissionOptionsResponseDTO  update (Long id, CommissionOptionsRequestDTO request){
    }
    public void delete (Long id){
    }

}