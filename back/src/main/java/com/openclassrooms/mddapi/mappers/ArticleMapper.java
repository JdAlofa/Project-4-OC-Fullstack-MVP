package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(source = "user.username", target = "authorUsername")
    @Mapping(source = "theme.id", target = "themeId")
    ArticleDto toDto(Article article);

}