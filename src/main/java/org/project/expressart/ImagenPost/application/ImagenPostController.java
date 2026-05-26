package org.project.expressart.ImagenPost.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenPost.domain.PostPictureService;
import org.project.expressart.ImagenPost.dto.ImagenPostResponseDTO;
import org.project.expressart.ImagenPost.dto.ImagenPostUpdateDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{postId}/images/{imageId}")
    public ResponseEntity<ImagenPostResponseDTO> getById(
            @PathVariable Long postId, @PathVariable Long imageId) throws ResourceNotFoundException{
        return ResponseEntity.ok(postPictureService.getById(postId,imageId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ImagenPostResponseDTO> create(@PathVariable Long postId,
            @RequestPart("file")MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postPictureService.create(postId,file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenPostResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenPostUpdateDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(postPictureService.update(id, dto));
    }

    @DeleteMapping("/{postId}/images/{imageId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId, @PathVariable Long imageId) {
        postPictureService.delete(postId,imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteByPost(@PathVariable Long postId) {
        postPictureService.deleteByPost(postId);
        return ResponseEntity.noContent().build();
    }
}