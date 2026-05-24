package org.project.expressart.Post.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<PostResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.findAllBy(pageable);
    }
    public PostResponseDTO  findById (Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }
    public PostResponseDTO findByPortafolioId (Long portafolioId){
        Post post = postRepository.findByPortafolioId(portafolioId).orElseThrow(()-> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }
    public PostResponseDTO findByPortafolioIdAndEsPublico (Long portafolioId, Boolean publicStatus){
        Post post = postRepository.findByPortafolioIdAndEsPublico(portafolioId, publicStatus).orElseThrow(()-> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);

    }
    public PostResponseDTO findByCategoriaId (Long categoriaId){
        Post post = postRepository.findByCategoriaId(categoriaId).orElseThrow(()-> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }
    public PostResponseDTO findByTagId (Long tagId){
        Post post = postRepository.findByTagId(tagId).orElseThrow(()-> new ResourceNotFoundEXception("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO create(PostRequestDTO request){
    }
    public PostResponseDTO  update (Long id, PostRequestDTO request){
    }
    public void delete (Long id){
        if (postRepository.existsById(id))
            postRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post with ID " + id + " doesn't exist");
    }
}