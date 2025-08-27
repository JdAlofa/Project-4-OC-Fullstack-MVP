package com.openclassrooms.mddapi.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;
}
