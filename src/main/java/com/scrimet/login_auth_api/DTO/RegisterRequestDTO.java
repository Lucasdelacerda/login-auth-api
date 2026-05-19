package com.scrimet.login_auth_api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDTO (
        @NotBlank(message = "O nome não pode estar em branco")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O e-mail deve ser válido (ex: usuario@provedor.com)")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{6,}$",
                message = "A senha deve ter no mínimo 6 caracteres, pelo menos uma letra maiúscula e um caractere especial (!@#$%^&*...)"
        )
        String password){

}
