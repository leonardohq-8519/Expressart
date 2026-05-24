package org.project.expressart.Tags.domain;

import lombok.RequiredArgsConstructor;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {
    public List<TagsResponseDTO> findAll(){
    }
    public TagsResponseDTO  findById (Long id){
    }
    public TagsResponseDTO findByNombre (String name){
    }
    public TagsResponseDTO create(TagsRequestDTO request){
    }
    public TagsResponseDTO update(Long id, TagsRequestDTO request){
    }
    public void delete (Long id){
    }
}
