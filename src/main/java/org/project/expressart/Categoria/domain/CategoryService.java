package org.project.expressart.Categoria.domain;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Categoria.dto.CategoryRequestDTO;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoriaRepository categoryRepository;
    public List<CategoryResponseDTO> findAll(){
    }
    public CategoryResponseDTO  findById (Long id){
    }
    public CategoryResponseDTO findByNombre (String name){
    }
    public CategoryResponseDTO create(CategoryRequestDTO request){
    }
    public CategoryResponseDTO  update (Long id, CategoryRequestDTO request){
    }
    public void delete (Long id){
    }


}
