package org.project.expressart.Portafolio.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortafolioService{
    private final PortafolioRepository portafolioRepository;
    public List<PortafolioResponseDTO> findAll(){
    }
    public PortafolioResponseDTO  findById (Long id){
    }
    public PortafolioResponseDTO findByPerfilArtistaId (Long perfilArtistaId){
    }
    public PortafolioResponseDTO findByPerfilArtistaIdAndEsPublico (Long perfilArtistaId, Boolean status){
    }


    public PortafolioResponseDTO create(PortafolioRequestDTO request){
    }
    public PortafolioResponseDTO  update (Long id, PortafolioRequestDTO request){
    }
    public void delete (Long id){
    }
}