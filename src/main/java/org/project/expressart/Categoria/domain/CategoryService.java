package org.project.expressart.Categoria.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.dto.CategoryRequestDTO;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
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
    public CategoryResponseDTO  findById (Long id)throws ResourceNotFoundException {
        Categoria category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    public CategoryResponseDTO findByNombre (String name)throws ResourceNotFoundException{
        Categoria category = categoryRepository.findByNombre(name).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    public CategoryResponseDTO create(CategoryRequestDTO request){
        Categoria category = new Categoria();
        category.setNombre(request.getNombre());
        category.setDescripcion(request.getDescripcion());
        category.setIconoUrl(request.getIconoUrl());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryResponseDTO.class);
    }
    public CategoryResponseDTO  update (Long id, CategoryRequestDTO request)throws ResourceNotFoundException{
        Categoria updatedCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (request.getNombre() != null && !request.getNombre().isEmpty())
            updatedCategory.setNombre(request.getNombre());
        updatedCategory.setDescripcion(request.getDescripcion());
        updatedCategory.setIconoUrl(request.getIconoUrl());
        categoryRepository.save(updatedCategory);
        return modelMapper.map(updatedCategory, CategoryResponseDTO.class);
    }
    public void delete (Long id){
        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Category with ID " + id + " doesn't exist");
    }


}
