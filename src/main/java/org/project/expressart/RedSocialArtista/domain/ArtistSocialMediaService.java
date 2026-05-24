package org.project.expressart.RedSocialArtista.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.RedSocialArtista.infrastructure.RedSocialArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistSocialMediaService {
    @Autowired
    private final RedSocialArtistaRepository artistSocialMediaRepository;
    @Autowired
    private ModelMapper modelMapper;

}
