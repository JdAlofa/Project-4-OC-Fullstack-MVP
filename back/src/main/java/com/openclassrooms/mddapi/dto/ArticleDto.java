package com.openclassrooms.mddapi.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private Long themeId;
    private String themeTitle;
    private String authorUsername;
    private LocalDateTime createdAt;
    private List<CommentDto> comments; 
}