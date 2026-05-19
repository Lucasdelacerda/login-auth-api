package com.scrimet.login_auth_api.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "Informe seu email")
        String email,
        @NotBlank(message = "Informe sua senha")
        String password){
}
