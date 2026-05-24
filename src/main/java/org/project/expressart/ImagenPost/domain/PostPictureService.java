package org.project.expressart.ImagenPost.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ArchivoPost.dto.ArchivoPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostCreateDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostUpdateDTO;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostRepository;
import org.project.expressart.Mensaje.domain.Mensaje;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostPictureService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final ImagenPostRepository postPictureRepository;
    @Autowired
    private final PostRepository postRepository;

    public List<ImagenPostResponseDTO> getByPostId(Long postId) throws ResourceNotFoundException {
        List<ImagenPost> postPicture = postPictureRepository.findByPostId(postId);
        if (postPicture.isEmpty()) {
            throw new ResourceNotFoundException("No post pictures found for post id: " + postId);
        }
        return postPicture.stream()
                .map(ticket -> modelMapper.map(postPicture, ImagenPostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ImagenPostResponseDTO getById(Long id) throws ResourceNotFoundException {
        ImagenPost postPicture = postPictureRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post picture not found"));
        return modelMapper.map(postPicture, ImagenPostResponseDTO.class);
    }
    public ImagenPostResponseDTO create(ImagenPostCreateDTO dto){
        ImagenPost postPicture = new ImagenPost();
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(()-> new EntityNotFoundException("Post picture not found"));
        postPicture.setPost(post);
        postPicture.setUrl(dto.getUrl());
        postPicture.setOrden(dto.getOrden());
        postPictureRepository.save(postPicture);
        return modelMapper.map(postPicture, ImagenPostResponseDTO.class);
    }

    public ImagenPostResponseDTO update(Long id, ImagenPostUpdateDTO dto)throws ResourceNotFoundException{
        ImagenPost postPicture = postPictureRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post picture not found"));;
        postPicture.setUrl(dto.getUrl());
        postPicture.setOrden(dto.getOrden());
        postPictureRepository.save(postPicture);
        return modelMapper.map(postPicture, ImagenPostResponseDTO.class);
    }

    public void delete(Long id){
        if (postPictureRepository.existsById(id))
            postPictureRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post picture with ID " + id + " doesn't exist");
    }
    public void deleteByPost(Long postId){
        if (postPictureRepository.existsByPostId(postId))
            postPictureRepository.deleteByPostId(postId);
        else
            throw new EntityNotFoundException("Post picture from post ID " + postId + " doesn't exist");
    }
}
