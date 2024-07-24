package org.example.userauthenticationservice_june2024.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateTokenRequestDto {
    private String token;
    private Long userId;
}
