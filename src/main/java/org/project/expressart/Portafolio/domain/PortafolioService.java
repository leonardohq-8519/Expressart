package org.project.expressart.Portafolio.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortafolioService {

    @Autowired
    private final PortafolioRepository portafolioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PortafolioResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Portafolio> portafolios = portafolioRepository.findAll(pageable).getContent();
        return convertToDtoList(portafolios);
    }

    public PortafolioResponseDTO findById(Long id) {
        Portafolio portafolio = portafolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Portafolio not found"));
        return modelMapper.map(portafolio, PortafolioResponseDTO.class);
    }

    public List<PortafolioResponseDTO> findByPerfilArtistaId(Long perfilArtistaId) {
        List<Portafolio> portafolios = portafolioRepository.findByPerfilArtistaId(perfilArtistaId);
        return convertToDtoList(portafolios);
    }

    public List<PortafolioResponseDTO> findByPerfilArtistaIdAndEsPublico(Long perfilArtistaId, Boolean status) {
        List<Portafolio> portafolios = portafolioRepository.findByPerfilArtistaIdAndEsPublico(perfilArtistaId, status);
        return convertToDtoList(portafolios);
    }

    public PortafolioResponseDTO create(PortafolioRequestDTO request) {
        Portafolio portafolio = modelMapper.map(request, Portafolio.class);
        Portafolio savedPortfolio = portafolioRepository.save(portafolio);
        return modelMapper.map(savedPortfolio, PortafolioResponseDTO.class);
    }

    public PortafolioResponseDTO update(Long id, PortafolioRequestDTO request) {
        Portafolio existingPortfolio = portafolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Portafolio not found"));

        modelMapper.map(request, existingPortfolio);
        existingPortfolio.setId(id);

        Portafolio updatedPortfolio = portafolioRepository.save(existingPortfolio);
        return modelMapper.map(updatedPortfolio, PortafolioResponseDTO.class);
    }

    public void delete(Long id) {
        if (portafolioRepository.existsById(id))
            portafolioRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Portfolio with ID " + id + " doesn't exist");
    }

    private List<PortafolioResponseDTO> convertToDtoList(List<Portafolio> portafolios) {
        return portafolios.stream()
                .map(portafolio -> modelMapper.map(portafolio, PortafolioResponseDTO.class))
                .collect(Collectors.toList());
    }
}