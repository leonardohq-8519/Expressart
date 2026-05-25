package org.project.expressart.ImagenComision.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/imagen-comisiones")
@RequiredArgsConstructor
public class ImagenComisionController {

    @GetMapping("/comision/{comisionId}")
    public ResponseEntity<List<Object>> getImagenesByComision(@PathVariable Long comisionId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}