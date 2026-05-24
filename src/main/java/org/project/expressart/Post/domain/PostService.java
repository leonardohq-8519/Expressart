package org.project.expressart.Post.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PostResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findAll(pageable).getContent();
        return convertToDtoList(posts);
    }

    public PostResponseDTO findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public List<PostResponseDTO> findByPortafolioId(Long portafolioId) {
        List<Post> posts = postRepository.findByPortafolioId(portafolioId);
        return convertToDtoList(posts);
    }

    public List<PostResponseDTO> findByPortafolioIdAndEsPublico(Long portafolioId, Boolean publicStatus) {
        List<Post> posts = postRepository.findByPortafolioIdAndEsPublico(portafolioId, publicStatus);
        return convertToDtoList(posts);
    }

    public List<PostResponseDTO> findByCategoriaId(Long categoriaId) {
        List<Post> posts = postRepository.findByCategoriaId(categoriaId);
        return convertToDtoList(posts);
    }

    public List<PostResponseDTO> findByTagId(Long tagId) {
        List<Post> posts = postRepository.findByTagId(tagId);
        return convertToDtoList(posts);
    }

    public PostResponseDTO create(PostRequestDTO request) {
        Post post = modelMapper.map(request, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostResponseDTO.class);
    }

    public PostResponseDTO update(Long id, PostRequestDTO request) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Post not found"));

        modelMapper.map(request, existingPost);
        existingPost.setId(id);

        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostResponseDTO.class);
    }

    public void delete(Long id) {
        if (postRepository.existsById(id))
            postRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post with ID " + id + " doesn't exist");
    }

    private List<PostResponseDTO> convertToDtoList(List<Post> posts) {
        return posts.stream()
                .map(post -> modelMapper.map(post, PostResponseDTO.class))
                .collect(Collectors.toList());
    }
}