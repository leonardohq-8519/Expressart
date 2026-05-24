package org.project.expressart.Categoria.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Categoria.dto.CategoryRequestDTO;
import org.project.expressart.Categoria.dto.CategoryResponseDTO;
import org.project.expressart.Categoria.infrastructure.CategoriaRepository;
import org.project.expressart.exception.ResourceNotFoundEXception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final CategoriaRepository categoryRepository;

    public List<CategoryResponseDTO> findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Categoria> categorias = categoryRepository.findAllBy(pageable).getContent();
        return convertToDtoList(categorias);
    }

    public CategoryResponseDTO findById(Long id) {
        Categoria category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO findByNombre(String name) {
        Categoria category = categoryRepository.findByNombre(name)
                .orElseThrow(() -> new ResourceNotFoundEXception("Category not found"));
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        if (categoryRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Category with name '" + request.getNombre() + "' already exists");
        }
        Categoria category = modelMapper.map(request, Categoria.class);
        Categoria savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO request) {
        Categoria existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEXception("Category not found"));

        modelMapper.map(request, existingCategory);
        existingCategory.setId(id);

        Categoria updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryResponseDTO.class);
    }

    public void delete(Long id) {
        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Category with ID " + id + " doesn't exist");
    }

    private List<CategoryResponseDTO> convertToDtoList(List<Categoria> categorias) {
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }
}