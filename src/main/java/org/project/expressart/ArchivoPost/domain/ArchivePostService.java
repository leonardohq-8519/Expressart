package org.project.expressart.ArchivoPost.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostUpdateDTO;
import org.project.expressart.ArchivoPost.infrastructure.ArchivoPostRepository;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchivePostService {
    @Autowired
    private ModelMapper modelMapper;
    private final ArchivoPostRepository archivePostRepository;
    private final PostRepository postRepository;
    public List<ArchivoPostResponseDTO> getByPost(Long postId) throws ResourceNotFoundException {
        List<ArchivoPost> postArchive = archivePostRepository.findByPostId(postId);
        if (postArchive.isEmpty()) {
            throw new ResourceNotFoundException("No post archives found for post id: " + postId);
        }
        return postArchive.stream()
                .map(pA -> modelMapper.map(pA, ArchivoPostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ArchivoPostResponseDTO getById(Long id) throws ResourceNotFoundException {
        ArchivoPost postArchive = archivePostRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post archive not found"));
        return modelMapper.map(postArchive, ArchivoPostResponseDTO.class);
    }

    public ArchivoPostResponseDTO create(ArchivoPostCreateDTO dto){
        ArchivoPost postArchive = new ArchivoPost();
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(()-> new EntityNotFoundException("Post archive not found"));
        postArchive.setPost(post);
        postArchive.setUrl(dto.getUrl());
        postArchive.setOrdenVisualizacion(dto.getOrdenVisualizacion());
        archivePostRepository.save(postArchive);
        return modelMapper.map(postArchive, ArchivoPostResponseDTO.class);
    }
    public ArchivoPostResponseDTO update(Long id, ArchivoPostUpdateDTO dto)throws ResourceNotFoundException{
        ArchivoPost postArchive = archivePostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post archive not found"));
        postArchive.setUrl(dto.getUrl());
        postArchive.setOrdenVisualizacion(dto.getOrdenVisualizacion());
        archivePostRepository.save(postArchive);
        return modelMapper.map(postArchive, ArchivoPostResponseDTO.class);
    }
    public void delete(Long id){
        if (archivePostRepository.existsById(id))
            archivePostRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post archive with ID " + id + " doesn't exist");
    }
    public void deleteByPost(Long postId){
        if (archivePostRepository.existsByPostId(postId))
            archivePostRepository.deleteByPostId(postId);
        else
            throw new EntityNotFoundException("Post archive from post ID " + postId + " doesn't exist");
    }
}
