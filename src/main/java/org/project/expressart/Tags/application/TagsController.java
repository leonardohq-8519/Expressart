package org.project.expressart.Tags.application;

import org.project.expressart.Tags.application.TagsService;
import org.project.expressart.Tags.application.dto.TagsRequestDTO;
import org.project.expressart.Tags.application.dto.TagsResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public ResponseEntity<List<TagsResponseDTO>> getAll() {
        return ResponseEntity.ok(tagsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagsService.findById(id));
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<TagsResponseDTO> getByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(tagsService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<TagsResponseDTO> create(@RequestBody TagsRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagsService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagsResponseDTO> update(
            @PathVariable Long id,
            @RequestBody TagsRequestDTO request) {
        return ResponseEntity.ok(tagsService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}