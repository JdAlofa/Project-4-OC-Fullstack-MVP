package com.openclassrooms.mddapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 20)
    private String username;

    @Size(max = 120)
    private String password;
}
