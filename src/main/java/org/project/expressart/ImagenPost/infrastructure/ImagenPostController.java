package org.project.expressart.ImagenPost.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/imagen-posts")
@RequiredArgsConstructor
public class ImagenPostController {

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Object>> getImagenesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}