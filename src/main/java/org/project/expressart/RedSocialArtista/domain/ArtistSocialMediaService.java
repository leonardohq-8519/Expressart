package org.project.expressart.RedSocialArtista.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.RedSocialArtista.dto.RedSocialArtistaCreateDTO;
import org.project.expressart.RedSocialArtista.dto.RedSocialArtistaResponseDTO;
import org.project.expressart.RedSocialArtista.dto.RedSocialArtistaUpdateDTO;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistSocialMediaService {
    private final RedSocialArtistaRepository artistSocialMediaRepository;
    private final PerfilArtistaRepository artistProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<RedSocialArtistaResponseDTO> getByArtist(Long perfilArtistaId) throws ResourceNotFoundException {
        List<RedSocialArtista> artistSocialMedia = artistSocialMediaRepository.findByPerfilArtistaId(perfilArtistaId);
        if (artistSocialMedia.isEmpty())
            throw new ResourceNotFoundException("No social media found for artist profile id: " + perfilArtistaId);
        return artistSocialMedia.stream()
                .map(socialMedia -> modelMapper.map(socialMedia, RedSocialArtistaResponseDTO.class))
                .collect(Collectors.toList());
    }
    public RedSocialArtistaResponseDTO getById(Long id) throws ResourceNotFoundException {
        RedSocialArtista artistSocialMedia = artistSocialMediaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Artist social media not found"));
        return modelMapper.map(artistSocialMedia, RedSocialArtistaResponseDTO.class);
    }

    public RedSocialArtistaResponseDTO create(RedSocialArtistaCreateDTO dto){
        RedSocialArtista artistSocialMedia = new RedSocialArtista();
        PerfilArtista artistProfile = artistProfileRepository.findById(dto.getPerfilArtistaId()).orElseThrow(() -> new EntityNotFoundException("Artist profile not found"));
        artistSocialMedia.setPerfilArtista(artistProfile);
        artistSocialMedia.setPlataforma(dto.getPlataforma());
        artistSocialMedia.setUrl(dto.getUrl());
        artistSocialMediaRepository.save(artistSocialMedia);
        return modelMapper.map(artistSocialMedia, RedSocialArtistaResponseDTO.class);
    }

    public RedSocialArtistaResponseDTO update(Long id, RedSocialArtistaUpdateDTO dto) throws ResourceNotFoundException {
        RedSocialArtista artistSocialMedia = artistSocialMediaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist social media not found"));
        artistSocialMedia.setUrl(dto.getUrl());
        artistSocialMediaRepository.save(artistSocialMedia);
        return modelMapper.map(artistSocialMedia, RedSocialArtistaResponseDTO.class);
    }

    public void delete(Long id){
        if (artistSocialMediaRepository.existsById(id))
            artistSocialMediaRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist social media with ID " + id + " doesn't exist");
    }

    public void deleteByArtist(Long perfilArtistaId){
        if (artistSocialMediaRepository.existsByPerfilArtista(perfilArtistaId))
            artistSocialMediaRepository.deleteByPerfilArtistaId(perfilArtistaId);
        else
            throw new EntityNotFoundException("Artist social media with artist profile ID " + perfilArtistaId + " doesn't exist");
    }
}
