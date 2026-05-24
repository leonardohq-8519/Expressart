package org.project.expressart.PerfilArtista.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistProfileService {
    @Autowired
    private final PerfilArtistaRepository artistProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ArtistProfileResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return artistProfileRepository.findAllBy(pageable);
    }
    public ArtistProfileResponseDTO  findById (Long id){
        PerfilArtista artistProfile = artistProfileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }
    public ArtistProfileResponseDTO findByUsuarioId (Long userId){
        PerfilArtista artistProfile = artistProfileRepository.findByUsuarioId(userId).orElseThrow(()-> new ResourceNotFoundEXception("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }
    public ArtistProfileResponseDTO create(ArtistProfileRequestDTO request){
    }
    public ArtistProfileResponseDTO  update (Long id, ArtistProfileRequestDTO request){
    }
    public void delete (Long id){
        if (artistProfileRepository.existsById(id))
            artistProfileRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist profile with ID " + id + " doesn't exist");
    }

}
