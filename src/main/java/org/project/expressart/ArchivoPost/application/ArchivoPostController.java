package org.project.expressart.ArchivoPost.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ArchivoPost.domain.ArchivePostService;
import org.project.expressart.ArchivoPost.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.dto.ArchivoPostUpdateDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-files")
@RequiredArgsConstructor
public class ArchivoPostController {

    private final ArchivePostService archivoPostService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ArchivoPostResponseDTO>> getByPost(
            @PathVariable Long postId) throws ResourceNotFoundException {
        return ResponseEntity.ok(archivoPostService.getByPost(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivoPostResponseDTO> getById(
            @PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(archivoPostService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ArchivoPostResponseDTO> create(
            @RequestBody ArchivoPostCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(archivoPostService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArchivoPostResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ArchivoPostUpdateDTO dto) throws ResourceNotFoundException{
        return ResponseEntity.ok(archivoPostService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        archivoPostService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteByPost(@PathVariable Long postId) {
        archivoPostService.deleteByPost(postId);
        return ResponseEntity.noContent().build();
    }
}