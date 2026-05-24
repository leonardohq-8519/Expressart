package org.project.expressart.ImagenPost.infrastructure;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenPost.application.dto.ImagenPostCreateDTO;
import org.project.expressart.ImagenPost.application.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.application.dto.ImagenPostUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class ImagenPostController {

    private final ImagenPostService imagenPostService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ImagenPostResponseDTO>> getByPost(
            @PathVariable Long postId) {
        return ResponseEntity.ok(imagenPostService.getByPost(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenPostResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(imagenPostService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ImagenPostResponseDTO> create(
            @RequestBody ImagenPostCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(imagenPostService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenPostResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenPostUpdateDTO dto) {
        return ResponseEntity.ok(imagenPostService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imagenPostService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteByPost(@PathVariable Long postId) {
        imagenPostService.deleteByPost(postId);
        return ResponseEntity.noContent().build();
    }
}