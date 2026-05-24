package org.project.expressart.Portafolio.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortafolioService{
    @Autowired
    private final PortafolioRepository portafolioRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<PortafolioResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return portafolioRepository.findAllBy(pageable);
    }
    public PortafolioResponseDTO  findById (Long id){
        Portafolio portafolio = portafolioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Portafolio not found"));
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }
    public PortafolioResponseDTO findByPerfilArtistaId (Long perfilArtistaId){
        Portafolio portafolio = portafolioRepository.findByPerfilArtistaId(perfilArtistaId).orElseThrow(()-> new ResourceNotFoundEXception("Portafolio not found"));
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }
    public PortafolioResponseDTO findByPerfilArtistaIdAndEsPublico (Long perfilArtistaId, Boolean status){
        Portafolio portafolio = portafolioRepository.findByPerfilArtistaIdAndEsPublico(perfilArtistaId, status).orElseThrow(()-> new ResourceNotFoundEXception("Portafolio not found"));
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }


    public PortafolioResponseDTO create(PortafolioRequestDTO request){
        Portafolio portafolio = new Portafolio();
        portafolio.setTitulo(request.getTitulo());
        portafolio.setDescripcion(request.getDescripcion());
        portafolio.setPortada_url(request.getPortada_url());
        portafolioRepository.save(portafolio);
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }
    public PortafolioResponseDTO  update (Long id, PortafolioRequestDTO request){
        Portafolio updPortafolio = portafolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Portafolio not found"));
        if (request.getTitulo()!= null && !request.getTitulo().isEmpty())
            updPortafolio.setTitulo(request.getTitulo());
        updPortafolio.setDescripcion(request.getDescripcion());
        updPortafolio.setPortada_url(request.getPortada_url());
        portafolioRepository.save(updPortafolio);
        return modelMapper.map(updPortafolio, PortafolioResponseDTO.class);
    }
    public void delete (Long id){
        if (portafolioRepository.existsById(id))
            portafolioRepository.deleteById(id);
        else
            throw new EntityNotFoundException("User with ID " + id + " doesn't exist");
    }
}