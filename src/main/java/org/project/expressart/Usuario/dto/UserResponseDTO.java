package org.project.expressart.Usuario.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String avatar_url;
    private String biography;
    private ZonedDateTime registerDate;
}