package org.project.expressart.Comision.domain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.PerfilArtista.domain.PerfilArtista;
import org.project.expressart.PerfilArtista.infrastructure.PerfilArtistaRepository;
import org.project.expressart.Tags.domain.Tags;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.project.expressart.exceptions.InvalidStateTransitionException;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.project.expressart.exceptions.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionService{
    @Autowired
    private ModelMapper modelMapper;
    private final ComisionRepository commissionRepository;

    private final PerfilArtistaRepository artistProfileRepository;
    private final CategoriaRepository categoryRepository;
    private final TagsRepository tagsRepository;
    public List<CommissionResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return commissionRepository.findAllBy(pageable);
    }
    public CommissionResponseDTO  findById (Long id) throws ResourceNotFoundException {
        Comision commission = commissionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }


    public List<CommissionResponseDTO>  findByCategoriaId (Long categoryId)throws ResourceNotFoundException{
        List<Comision> commission = commissionRepository.findByCategoriaId(categoryId);
        if (commission.isEmpty()) {
            throw new ResourceNotFoundException("No commissions found for category id: " + categoryId);
        }
        return commission.stream()
                .map(c -> modelMapper.map(c, CommissionResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommissionResponseDTO> findByPerfilArtistaId (Long artistaId)throws ResourceNotFoundException{
        List<Comision> commission = commissionRepository.findByPerfilArtistaId(artistaId);
        if (commission.isEmpty()) {
            throw new ResourceNotFoundException("No commissions found for artist id: " + artistaId);
        }
        return commission.stream()
                .map(c -> modelMapper.map(c, CommissionResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<CommissionResponseDTO> findByPerfilArtistaIdAndEstaActiva (Long artistaId, Boolean status)throws ResourceNotFoundException{
        List<Comision> commission = commissionRepository.findByPerfilArtistaIdAndEstaActiva(artistaId, status);
        if (commission.isEmpty()) {
            throw new ResourceNotFoundException("No active commissions found for artist id: " + artistaId);
        }
        return commission.stream()
                .map(c -> modelMapper.map(c, CommissionResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<CommissionResponseDTO> findByTagsId (Long tagId)throws ResourceNotFoundException{
        List<Comision> commission = commissionRepository.findByTagsId(tagId);
        if (commission.isEmpty()) {
            throw new ResourceNotFoundException("No commissions found for tag id: " + tagId);
        }
        return commission.stream()
                .map(c -> modelMapper.map(c, CommissionResponseDTO.class))
                .collect(Collectors.toList());
    }


    public CommissionResponseDTO create(CommissionRequestDTO request)throws ResourceNotFoundException{
        Comision commission = new Comision();
        PerfilArtista artistProfile = artistProfileRepository.findById(request.getPerfilArtistaId()).orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));
        commission.setPerfilArtista(artistProfile);
        commission.setTitulo(request.getTitulo());
        commission.setDescripcion(request.getDescripcion());
        commission.setPortadaUrl(request.getPortadaUrl());
        commission.setEstaActiva(request.getEstaActiva());
        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new ResourceNotFoundException("Not all categories were found");
        }
        commission.setCategorias(categories);
        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new ResourceNotFoundException("Not all tags were found");
        }
        commission.setTags(tags);
        commissionRepository.save(commission);
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO  update (Long id, CommissionRequestDTO request)throws ResourceNotFoundException{
        Comision updatedCommission = commissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commission not found"));
        PerfilArtista artistProfile = artistProfileRepository.findById(request.getPerfilArtistaId()).orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));
        if (request.getPerfilArtistaId() != null)
            updatedCommission.setPerfilArtista(artistProfile);

        if (request.getPerfilArtistaId() != null && !updatedCommission.getPerfilArtista().getId().equals(request.getPerfilArtistaId())) {
            throw new UnauthorizedActionException("You are not authorized to transfer this commission to another artist profile.");
        }

        if (updatedCommission.getEstaActiva() && !request.getEstaActiva()) {
            if (!updatedCommission.getImagenes().isEmpty()) {
                throw new InvalidStateTransitionException("You cannot deactivate or cancel a commission that already has progress images");
            }
        }

        if (request.getTitulo() != null && !request.getTitulo().isEmpty())
            updatedCommission.setTitulo(request.getTitulo());
        updatedCommission.setDescripcion(request.getDescripcion());
        updatedCommission.setPortadaUrl(request.getPortadaUrl());
        updatedCommission.setEstaActiva(request.getEstaActiva());
        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new ResourceNotFoundException("Not all categories were found");
        }
        updatedCommission.setCategorias(categories);
        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new ResourceNotFoundException("Not all tags were found");
        }
        updatedCommission.setTags(tags);
        commissionRepository.save(updatedCommission);
        return modelMapper.map(updatedCommission, CommissionResponseDTO.class);
    }
    public void delete (Long id) throws ResourceNotFoundException {
        if (commissionRepository.existsById(id))
            commissionRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("Commission with ID " + id + " doesn't exist");
    }

}