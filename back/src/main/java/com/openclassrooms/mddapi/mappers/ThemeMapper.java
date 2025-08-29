package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.models.Theme;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ThemeMapper {
    ThemeDto toDto(Theme theme);

}