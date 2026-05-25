package org.project.expressart.Portafolio.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortafolioService {

    private final PortafolioRepository portafolioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<PortafolioResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return portafolioRepository.findAllBy(pageable);
    }

    public PortafolioResponseDTO findById (Long id) throws ResourceNotFoundException {
        Portafolio portafolio = portafolioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Portafolio not found"));
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }

    public List<PortafolioResponseDTO> findByPerfilArtistaId (Long perfilArtistaId) throws ResourceNotFoundException {
        List<Portafolio> portafolioList = portafolioRepository.findByPerfilArtistaId(perfilArtistaId);
        if (portafolioList.isEmpty()) {
            throw new ResourceNotFoundException("No portafolios found for artist id: " + perfilArtistaId);
        }
        return portafolioList.stream()
                .map(p -> modelMapper.map(p, PortafolioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<PortafolioResponseDTO> findByPerfilArtistaIdAndEsPublico (Long perfilArtistaId, Boolean status) throws ResourceNotFoundException {
        List<Portafolio> portafolioList = portafolioRepository.findByPerfilArtistaIdAndEsPublico(perfilArtistaId, status);
        if (portafolioList.isEmpty()) {
            throw new ResourceNotFoundException("No public portafolios found for artist id: " + perfilArtistaId);
        }
        return portafolioList.stream()
                .map(p -> modelMapper.map(p, PortafolioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PortafolioResponseDTO create(PortafolioRequestDTO request){
        Portafolio portafolio = new Portafolio();
        portafolio.setTitulo(request.getTitulo());
        portafolio.setDescripcion(request.getDescripcion());
        portafolio.setPortada_url(request.getPortada_url());
        portafolioRepository.save(portafolio);
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }

    public PortafolioResponseDTO update (Long id, PortafolioRequestDTO request) throws ResourceNotFoundException {
        Portafolio updPortafolio = portafolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Portafolio not found"));
        if (request.getTitulo() != null && !request.getTitulo().isEmpty())
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
            throw new EntityNotFoundException("Portafolio with ID " + id + " doesn't exist");
    }
}