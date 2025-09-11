package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ThemeMapper.class})
public interface UserMapper {

    UserDto toDto(User user);

}