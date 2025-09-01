package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user.username", target = "authorUsername")
    @Mapping(source = "article.id", target = "articleId")
    CommentDto toDto(Comment comment);
} 