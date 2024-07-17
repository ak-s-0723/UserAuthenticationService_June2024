package org.example.userauthenticationservice_june2024.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthenticationservice_june2024.models.Role;

@Setter
@Getter
public class UserDto {
    private String email;

    private Role role;
}
