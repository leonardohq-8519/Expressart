package org.project.expressart.ImagenPost.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ReorderImagesDTO(
    @NotEmpty(message = "Id list cannot be empty")
    List<Long> orderIds
) {}
