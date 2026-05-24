package org.project.expressart.ImagenPost.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenPost.domain.PostPictureService;
import org.project.expressart.ImagenPost.dto.ImagenPostCreateDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostUpdateDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class ImagenPostController {

    private final PostPictureService postPictureService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ImagenPostResponseDTO>> getByPost(
            @PathVariable Long postId) throws ResourceNotFoundException{
        return ResponseEntity.ok(postPictureService.getByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenPostResponseDTO> getById(
            @PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(postPictureService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ImagenPostResponseDTO> create(
            @RequestBody ImagenPostCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postPictureService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenPostResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenPostUpdateDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(postPictureService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postPictureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteByPost(@PathVariable Long postId) {
        postPictureService.deleteByPost(postId);
        return ResponseEntity.noContent().build();
    }
}