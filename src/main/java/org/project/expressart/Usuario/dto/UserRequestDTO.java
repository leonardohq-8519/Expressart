package org.project.expressart.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String username;
    private String email;
    private String name;
    private String password;
    private String avatar_url;
    private String biography;
}