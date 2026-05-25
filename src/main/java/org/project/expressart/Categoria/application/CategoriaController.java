package org.project.expressart.Categoria.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.Categoria.domain.CategoryService;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoryResponseDTO> findByNombre(@PathVariable String nombre) throws Exception {
        return ResponseEntity.ok(categoryService.findByNombre(nombre));
    }
}