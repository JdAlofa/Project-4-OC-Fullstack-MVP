package com.openclassrooms.mddapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateArticleDto {

    @NotNull(message = "Theme ID cannot be null")
    private Long themeId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 50)
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 2000)
    private String content;
}
