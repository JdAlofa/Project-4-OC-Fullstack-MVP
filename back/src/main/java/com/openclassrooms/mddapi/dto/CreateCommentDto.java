package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateCommentDto {
    @NotBlank
    private String content;
}
