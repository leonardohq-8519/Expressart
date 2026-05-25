package org.project.expressart.ArchivoPost.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostCreateDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostResponseDTO;
import org.project.expressart.ArchivoPost.application.dto.ArchivoPostUpdateDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArchivePostServiceTest {

    private ArchivePostService archivePostService;

    @BeforeEach
    void setUp() {
        archivePostService = new ArchivePostService();
    }

    @Test
    @DisplayName("Debería retornar lista vacía al obtener archivos por post")
    void shouldReturnEmptyListWhenGetByPostId() {
        List<ArchivoPostResponseDTO> result = archivePostService.getByPost(1L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debería retornar DTO vacío al obtener archivo por ID")
    void shouldReturnEmptyDtoWhenGetById() {
        ArchivoPostResponseDTO result = archivePostService.getById(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Debería retornar DTO al crear un archivo de post")
    void shouldReturnDtoWhenCreatingArchivoPost() {
        ArchivoPostCreateDTO dto = new ArchivoPostCreateDTO(1L, "http://example.com/archivo.zip", 1);
        ArchivoPostResponseDTO result = archivePostService.create(dto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Debería retornar DTO al actualizar un archivo de post")
    void shouldReturnDtoWhenUpdatingArchivoPost() {
        ArchivoPostUpdateDTO dto = new ArchivoPostUpdateDTO("http://example.com/updated.zip", 2);
        ArchivoPostResponseDTO result = archivePostService.update(1L, dto);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Debería ejecutar delete sin lanzar excepción")
    void shouldNotThrowWhenDeletingArchivoPost() {
        assertDoesNotThrow(() -> archivePostService.delete(1L));
    }

    @Test
    @DisplayName("Debería ejecutar deleteByPost sin lanzar excepción")
    void shouldNotThrowWhenDeletingArchivoPostByPostId() {
        assertDoesNotThrow(() -> archivePostService.deleteByPost(1L));
    }
}