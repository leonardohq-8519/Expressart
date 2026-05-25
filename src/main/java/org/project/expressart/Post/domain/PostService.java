package org.project.expressart.Post.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.domain.Categoria;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.project.expressart.Post.infrastructure.PostRepository;
import org.project.expressart.Tags.domain.Tags;
import org.project.expressart.Tags.infrastructure.TagsRepository;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PortafolioRepository portafolioRepository;
    private final CategoriaRepository categoryRepository;
    private final TagsRepository tagsRepository;
    private final ModelMapper modelMapper;

    public List<PostResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.findAllBy(pageable);
    }

    public PostResponseDTO findById(Long id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public List<PostResponseDTO> findByPortafolioId(Long portafolioId) throws ResourceNotFoundException {
        List<Post> postList = postRepository.findByPortafolioId(portafolioId);
        if (postList.isEmpty()) {
            throw new ResourceNotFoundException("No posts found for portafolio id: " + portafolioId);
        }
        return postList.stream()
                .map(p -> modelMapper.map(p, PostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findByPortafolioIdAndEsPublico(Long portafolioId, Boolean publicStatus) throws ResourceNotFoundException {
        List<Post> postList = postRepository.findByPortafolioIdAndEsPublico(portafolioId, publicStatus);
        if (postList.isEmpty()) {
            throw new ResourceNotFoundException("No public posts found for portafolio id: " + portafolioId);
        }
        return postList.stream()
                .map(p -> modelMapper.map(p, PostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findByCategoriaId(Long categoriaId) throws ResourceNotFoundException {
        List<Post> postList = postRepository.findByCategoriaId(categoriaId);
        if (postList.isEmpty()) {
            throw new ResourceNotFoundException("No posts found for category id: " + categoriaId);
        }
        return postList.stream()
                .map(p -> modelMapper.map(p, PostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findByTagId(Long tagId) throws ResourceNotFoundException {
        List<Post> postList = postRepository.findByTagId(tagId);
        if (postList.isEmpty()) {
            throw new ResourceNotFoundException("No posts found for tag id: " + tagId);
        }
        return postList.stream()
                .map(p -> modelMapper.map(p, PostResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PostResponseDTO create(PostRequestDTO request) {
        Post post = new Post();
        Portafolio portafolio = portafolioRepository.findById(request.getPortafolioId()).orElseThrow(() -> new EntityNotFoundException("Portafolio not found"));
        post.setPortafolio(portafolio);
        post.setTitulo(request.getTitulo());
        post.setDescripcion(request.getDescripcion());
        post.setEsPublico(request.getEsPublico());

        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new EntityNotFoundException("Not all categories were found");
        }
        post.setCategorias(categories);

        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new EntityNotFoundException("Not all tags were found");
        }
        post.setTags(tags);

        postRepository.save(post);
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO update(Long id, PostRequestDTO request) throws ResourceNotFoundException {
        Post updatedPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Portafolio portafolio = portafolioRepository.findById(request.getPortafolioId()).orElseThrow(() -> new EntityNotFoundException("Portafolio not found"));
        updatedPost.setPortafolio(portafolio);

        if (request.getTitulo() != null && !request.getTitulo().isEmpty())
            updatedPost.setTitulo(request.getTitulo());
        if (request.getDescripcion() != null && !request.getDescripcion().isEmpty())
            updatedPost.setDescripcion(request.getDescripcion());

        updatedPost.setEsPublico(request.getEsPublico());

        List<Long> categoriaIds = request.getCategoriaIds();
        List<Categoria> categories = categoryRepository.findAllById(categoriaIds);
        if (categories.size() != categoriaIds.size()) {
            throw new EntityNotFoundException("Not all categories were found");
        }
        updatedPost.setCategorias(categories);

        List<Long> tagsIds = request.getTagIds();
        List<Tags> tags = tagsRepository.findAllById(tagsIds);
        if (tags.size() != tagsIds.size()) {
            throw new EntityNotFoundException("Not all tags were found");
        }
        updatedPost.setTags(tags);

        postRepository.save(updatedPost);
        return modelMapper.map(updatedPost, PostResponseDTO.class);
    }

    public void delete(Long id) {
        if (postRepository.existsById(id))
            postRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Post with ID " + id + " doesn't exist");
    }
}