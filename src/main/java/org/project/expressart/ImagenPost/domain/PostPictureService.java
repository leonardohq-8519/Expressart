package org.project.expressart.ImagenPost.domain;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.ImagenPost.application.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.application.dto.ImagenPostUpdateDTO;
import org.project.expressart.ImagenPost.infrastructure.ImagenPostRepository;
import org.project.expressart.Post.domain.Post;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.exception.ImageLimitException;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostPictureService {

    private final ModelMapper modelMapper;
    private final ImagenPostRepository imagenPostRepository;
    private final PostRepository postRepository;

    private static final int IMAGE_LIMIT = 4;

    public List<ImagenPostResponseDTO> getByPostId(Long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("No post pictures found for post id: " + postId));

        return post.getImagenes().stream()
                .map(image -> modelMapper.map(image, ImagenPostResponseDTO.class))
                .sorted(Comparator.comparingInt(ImagenPostResponseDTO::getOrden))
                .collect(Collectors.toList());
    }

    public ImagenPostResponseDTO getById(Long postId, Long imageId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));

        return post.getImagenes().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .map(img -> modelMapper.map(img, ImagenPostResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Image not found: " + imageId));
    }

    public ImagenPostResponseDTO create(Long postId, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        if (post.getImagenes().size() >= IMAGE_LIMIT) {
            throw new ImageLimitException("A post cannot have more than " + IMAGE_LIMIT + " images");
        }

        // Generamos una URL simulada para el almacenamiento local/remoto provisional
        String url = "/storage/posts/" + postId + "/" + file.getOriginalFilename();

        ImagenPost postPicture = new ImagenPost();
        postPicture.setPost(post);
        postPicture.setUrl(url);
        postPicture.setOrden(post.getImagenes().size());

        post.getImagenes().add(postPicture);
        imagenPostRepository.save(postPicture);

        return modelMapper.map(postPicture, ImagenPostResponseDTO.class);
    }

    public ImagenPostResponseDTO update(Long id, ImagenPostUpdateDTO dto) throws ResourceNotFoundException {
        ImagenPost postPicture = imagenPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post picture not found with ID: " + id));

        postPicture.setUrl(dto.getUrl());
        postPicture.setOrden(dto.getOrden());
        imagenPostRepository.save(postPicture);

        return modelMapper.map(postPicture, ImagenPostResponseDTO.class);
    }

    public void delete(Long postId, Long imageId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));

        ImagenPost image = post.getImagenes().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Image not found: " + imageId));

        post.getImagenes().remove(image);
        reorder(post.getImagenes());
        postRepository.save(post);
    }

    public void deleteByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));

        if (post.getImagenes().isEmpty()) {
            throw new EntityNotFoundException("Post: " + postId + " does not have images.");
        }

        post.getImagenes().clear();
        postRepository.save(post);
    }

    private void reorder(List<ImagenPost> images) {
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setOrden(i);
        }
    }
}