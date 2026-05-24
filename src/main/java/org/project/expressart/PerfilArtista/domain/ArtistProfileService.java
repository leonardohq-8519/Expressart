package org.project.expressart.PerfilArtista.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistProfileService {

    @Autowired
    private final PerfilArtistaRepository artistProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ArtistProfileResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<PerfilArtista> perfiles = artistProfileRepository.findAll(pageable).getContent();
        return convertToDtoList(perfiles);
    }

    public ArtistProfileResponseDTO findById(Long id) {
        PerfilArtista artistProfile = artistProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }

    public ArtistProfileResponseDTO findByUsuarioId(Long userId) {
        PerfilArtista artistProfile = artistProfileRepository.findByUsuarioId(userId)
                .orElseThrow(() -> new ResourceNotFoundEXception("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }

    public ArtistProfileResponseDTO create(Long usuarioId, ArtistProfileRequestDTO request) {
        PerfilArtista artistProfile = modelMapper.map(request, PerfilArtista.class);
        PerfilArtista savedProfile = artistProfileRepository.save(artistProfile);
        return modelMapper.map(savedProfile, ArtistProfileResponseDTO.class);
    }

    public ArtistProfileResponseDTO update(Long id, ArtistProfileRequestDTO request) {
        PerfilArtista existingProfile = artistProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Artist profile not found"));

        modelMapper.map(request, existingProfile);
        existingProfile.setId(id);

        PerfilArtista updatedProfile = artistProfileRepository.save(existingProfile);
        return modelMapper.map(updatedProfile, ArtistProfileResponseDTO.class);
    }

    public void delete(Long id) {
        if (artistProfileRepository.existsById(id))
            artistProfileRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist profile with ID " + id + " doesn't exist");
    }

    private List<ArtistProfileResponseDTO> convertToDtoList(List<PerfilArtista> perfiles) {
        return perfiles.stream()
                .map(perfil -> modelMapper.map(perfil, ArtistProfileResponseDTO.class))
                .collect(Collectors.toList());
    }
}