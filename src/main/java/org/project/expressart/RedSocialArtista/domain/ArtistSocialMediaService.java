package org.project.expressart.RedSocialArtista.domain;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistSocialMediaService {

    private final RedSocialArtistaRepository artistSocialMediaRepository;
    private final ModelMapper modelMapper;

    public List<RedSocialArtista> findByArtistaId(Long artistaId) {
        return artistSocialMediaRepository.findByPerfilArtistaId(artistaId);
    }

    public RedSocialArtista create(RedSocialArtista redSocial) {
        return artistSocialMediaRepository.save(redSocial);
    }

    public void delete(Long id) {
        if (!artistSocialMediaRepository.existsById(id)) {
            throw new EntityNotFoundException("Red social no encontrada");
        }
        artistSocialMediaRepository.deleteById(id);
    }
}