package org.project.expressart.PerfilArtista.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
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
    private final UsuarioRepository userRepository;
    @Autowired
    private final CategoriaRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<ArtistProfileResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return artistProfileRepository.findAllBy(pageable);
    }
    public ArtistProfileResponseDTO  findById (Long id)throws ResourceNotFoundException {
        PerfilArtista artistProfile = artistProfileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }
    public ArtistProfileResponseDTO findByUsuarioId (Long userId)throws ResourceNotFoundException{
        PerfilArtista artistProfile = artistProfileRepository.findByUsuarioId(userId).orElseThrow(()-> new ResourceNotFoundException("Artist profile not found"));
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }
    public ArtistProfileResponseDTO create(Long userId, ArtistProfileRequestDTO request){
        PerfilArtista artistProfile = new PerfilArtista();
        artistProfile.setComsDisponibles(request.getComsDisponibles());
        artistProfile.setTiempoEntregaPromedio(request.getTiempoEntregaPromedio());
        Usuario user = userRepository.findByUsername(request.getNombreUsuario())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        artistProfile.setUsuario(user);
        artistProfileRepository.save(artistProfile);
        return modelMapper.map(artistProfile, ArtistProfileResponseDTO.class);
    }
    public ArtistProfileResponseDTO  update (Long profId, ArtistProfileRequestDTO request)throws ResourceNotFoundException{
        PerfilArtista updArtistProfile = artistProfileRepository.findById(profId).orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));
        updArtistProfile.setComsDisponibles(request.getComsDisponibles());
        updArtistProfile.setTiempoEntregaPromedio(request.getTiempoEntregaPromedio());
        Usuario user = userRepository.findByUsername(request.getNombreUsuario())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        updArtistProfile.setUsuario(user);
        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllByArtistProfileId(profId);
        if (categories.size() != categoriaIds.size())
            throw new EntityNotFoundException("Not all categories were found");
        updArtistProfile.setCategorias(categories);
        artistProfileRepository.save(updArtistProfile);
        return modelMapper.map(updArtistProfile, ArtistProfileResponseDTO.class);
    }
    public void delete (Long id){
        if (artistProfileRepository.existsById(id))
            artistProfileRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Artist profile with ID " + id + " doesn't exist");
    }

}
