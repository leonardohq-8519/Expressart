package org.project.expressart.ArchivoPost.domain;

import org.springframework.stereotype.Service;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostUpdateDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArchivePostService {

    public List<ArchivoPostResponseDTO> getByPost(Long postId) {
        return new ArrayList<>();
    }

    public ArchivoPostResponseDTO getById(Long id) {
        return new ArchivoPostResponseDTO();
    }

    public ArchivoPostResponseDTO create(ArchivoPostCreateDTO dto) {
        return new ArchivoPostResponseDTO();
    }

    public ArchivoPostResponseDTO update(Long id, ArchivoPostUpdateDTO dto) {
        return new ArchivoPostResponseDTO();
    }

    public void delete(Long id) {
    }

    public void deleteByPost(Long postId) {
    }
}