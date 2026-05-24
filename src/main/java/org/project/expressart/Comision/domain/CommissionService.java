package org.project.expressart.Comision.domain;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommissionService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ComisionRepository commissionRepository;
    @Autowired
    private final PerfilArtistaRepository artistProfileRepository;
    @Autowired
    private final CategoriaRepository categoryRepository;
    @Autowired
    private final TagsRepository tagsRepository;
    public List<CommissionResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return commissionRepository.findAllBy(pageable);
    }
    public CommissionResponseDTO  findById (Long id){
        Comision commission = commissionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByPerfilArtistaId (Long artistaId){
        Comision commission = commissionRepository.findByPerfilArtistaId(artistaId).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByPerfilArtistaIdAndEstaActiva (Long artistaId, Boolean status){
        Comision commission = commissionRepository.findByPerfilArtistaIdAndEstaActiva(artistaId, status).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO findByTagsId (Long tagId){
        Comision commission = commissionRepository.findByTagsId(tagId).orElseThrow(()-> new ResourceNotFoundEXception("Commission not found"));
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO create(CommissionRequestDTO request){
        Comision commission = new Comision();
        PerfilArtista artistProfile = artistProfileRepository.findById(request.getPerfilArtistaId()).orElseThrow(() -> new EntityNotFoundException("Artist profile not found"));
        commission.setPerfilArtista(artistProfile);
        commission.setTitulo(request.getTitulo());
        commission.setDescripcion(request.getDescripcion());
        commission.setPortadaUrl(request.getPortadaUrl());
        commission.setEstaActiva(request.getEstaActiva());
        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new EntityNotFoundException("Not all categories were found");
        }
        commission.setCategorias(categories);
        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new EntityNotFoundException("Not all tags were found");
        }
        commission.setTags(tags);
        commissionRepository.save(commission);
        return modelMapper.map(commission, CommissionResponseDTO.class);
    }
    public CommissionResponseDTO  update (Long id, CommissionRequestDTO request){
        Comision updatedCommission = commissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commission not found"));
        PerfilArtista artistProfile = artistProfileRepository.findById(request.getPerfilArtistaId()).orElseThrow(() -> new EntityNotFoundException("Artist profile not found"));
        if (request.getPerfilArtistaId() != null)
            updatedCommission.setPerfilArtista(artistProfile);
        if (request.getTitulo() != null && !request.getTitulo().isEmpty())
            updatedCommission.setTitulo(request.getTitulo());
        updatedCommission.setDescripcion(request.getDescripcion());
        updatedCommission.setPortadaUrl(request.getPortadaUrl());
        updatedCommission.setEstaActiva(request.getEstaActiva());
        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new EntityNotFoundException("Not all categories were found");
        }
        updatedCommission.setCategorias(categories);
        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new EntityNotFoundException("Not all tags were found");
        }
        updatedCommission.setTags(tags);
        commissionRepository.save(updatedCommission);
        return modelMapper.map(updatedCommission, CommissionResponseDTO.class);
    }
    public void delete (Long id){
        if (commissionRepository.existsById(id))
            commissionRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Commission with ID " + id + " doesn't exist");
    }

}