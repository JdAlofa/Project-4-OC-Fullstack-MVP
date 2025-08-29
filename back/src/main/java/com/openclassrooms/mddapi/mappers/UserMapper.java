package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "themes", target = "themeIds")
    UserDto toDto(User user);

    default List<Long> mapThemesToIds(List<Theme> themes) {
        if (themes == null) {
            return Collections.emptyList();
        }
        return themes.stream()
                     .map(Theme::getId)
                     .collect(Collectors.toList());
    }
}