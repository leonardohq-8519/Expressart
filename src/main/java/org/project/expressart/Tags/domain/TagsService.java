package org.project.expressart.Tags.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {
    @Autowired
    private final TagsRepository tagsRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<TagsResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return tagsRepository.findAllBy(pageable);
    }
    public TagsResponseDTO  findById (Long id){
        Tags tags = tagsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Tag not found"));
        return modelMapper.map(tags, TagsResponseDTO.class);
    }
    public TagsResponseDTO findByNombre (String name){
        Tags tags = tagsRepository.findByNombre(name).orElseThrow(()-> new ResourceNotFoundEXception("Tag not found"));
        return modelMapper.map(tags, TagsResponseDTO.class);
    }
    public TagsResponseDTO create(TagsRequestDTO request){
        Tags tag = new Tags();
        tag.setNombre(request.getNombre());
        tagsRepository.save(tag);
        return modelMapper.map(tag, TagsResponseDTO.class);
    }
    public TagsResponseDTO update(Long id, TagsRequestDTO request){
        Tags updatedtag = tagsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        if (request.getNombre()!= null && !request.getNombre().isEmpty())
            updatedtag.setNombre(request.getNombre());
        tagsRepository.save(updatedtag);
        return modelMapper.map(updatedtag, TagsResponseDTO.class);
    }
    public void delete (Long id){
        if (tagsRepository.existsById(id))
            tagsRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Tag with ID " + id + " doesn't exist");
    }
}
