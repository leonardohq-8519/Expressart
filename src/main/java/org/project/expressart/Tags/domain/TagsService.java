package org.project.expressart.Tags.domain;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Tags.dto.TagsRequestDTO;
import org.project.expressart.Tags.dto.TagsResponseDTO;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.project.expressart.exceptions.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TagsResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return tagsRepository.findAllBy(pageable);
    }

    public TagsResponseDTO findById(Long id) throws ResourceNotFoundException {
        Tags tags = tagsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID: " + id));
        return modelMapper.map(tags, TagsResponseDTO.class);
    }

    public TagsResponseDTO findByNombre(String name) throws ResourceNotFoundException {
        Tags tags = tagsRepository.findByNombre(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with name: " + name));
        return modelMapper.map(tags, TagsResponseDTO.class);
    }

    @Transactional
    public TagsResponseDTO create(TagsRequestDTO request) {
        if (tagsRepository.existsByNombre(request.getNombre())) {
            throw new ResourceAlreadyExistsException("A tag with the name '" + request.getNombre() + "' already exists.");
        }

        Tags tag = new Tags();
        tag.setNombre(request.getNombre());
        tagsRepository.save(tag);
        return modelMapper.map(tag, TagsResponseDTO.class);
    }

    @Transactional
    public TagsResponseDTO update(Long id, TagsRequestDTO request) throws ResourceNotFoundException {
        Tags updatedtag = tagsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID: " + id));

        if (request.getNombre() != null && !request.getNombre().isEmpty()) {
            if (!updatedtag.getNombre().equalsIgnoreCase(request.getNombre()) && tagsRepository.existsByNombre(request.getNombre())) {
                throw new ResourceAlreadyExistsException("Cannot update tag. Another tag with the name '" + request.getNombre() + "' already exists.");
            }
            updatedtag.setNombre(request.getNombre());
        }

        tagsRepository.save(updatedtag);
        return modelMapper.map(updatedtag, TagsResponseDTO.class);
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        if (tagsRepository.existsById(id))
            tagsRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("Tag with ID " + id + " doesn't exist");
    }
}