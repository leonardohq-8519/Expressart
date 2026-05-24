package org.project.expressart.Categoria.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.dto.CategoryRequestDTO;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.Portafolio.domain.Portafolio;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final CategoriaRepository categoryRepository;
    public List<CategoryResponseDTO> findAll(){
        Pageable pageable = PageRequest.of(0, 10);
        return categoryRepository.findAllBy(pageable);
    }
    public CategoryResponseDTO  findById (Long id){
        Categoria category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEXception("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    public CategoryResponseDTO findByNombre (String name){
        Categoria category = categoryRepository.findByNombre(name).orElseThrow(()-> new ResourceNotFoundEXception("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    public CategoryResponseDTO create(CategoryRequestDTO request){
    }
    public CategoryResponseDTO  update (Long id, CategoryRequestDTO request){
    }
    public void delete (Long id){
        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Category with ID " + id + " doesn't exist");
    }


}
