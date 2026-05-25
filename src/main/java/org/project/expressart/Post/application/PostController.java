package org.project.expressart.Post.application;

import org.project.expressart.Post.domain.PostService;
import org.project.expressart.Post.dto.PostRequestDTO;
import org.project.expressart.Post.dto.PostResponseDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAll() {
        List<PostResponseDTO> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getById(@PathVariable Long id)throws ResourceNotFoundException {
        PostResponseDTO post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/portafolio/{portafolioId}")
    public ResponseEntity<List<PostResponseDTO>> getByPortafolio(@PathVariable Long portafolioId)throws ResourceNotFoundException {
        List<PostResponseDTO> posts = postService.findByPortafolioId(portafolioId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/portafolio/{portafolioId}/publicos")
    public ResponseEntity<List<PostResponseDTO>> getPublicosByPortafolio(@PathVariable Long portafolioId)throws ResourceNotFoundException {
        List<PostResponseDTO> posts = postService.findByPortafolioIdAndEsPublico(portafolioId, true);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<PostResponseDTO>> getByCategoria(@PathVariable Long categoriaId) throws ResourceNotFoundException{
        List<PostResponseDTO> posts = postService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<PostResponseDTO>> getByTag(@PathVariable Long tagId) throws ResourceNotFoundException {
        List<PostResponseDTO> posts = postService.findByTagId(tagId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostRequestDTO request) {
        PostResponseDTO created = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PostRequestDTO request)throws ResourceNotFoundException {
        PostResponseDTO updated = postService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}