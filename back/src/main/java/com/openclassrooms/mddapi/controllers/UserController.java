package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ThemeService themeService;
    private final UserMapper userMapper;

    public UserController(UserService userService, ThemeService themeService, UserMapper userMapper) {
        this.userService = userService;
        this.themeService = themeService;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     * @return ResponseEntity with the user's profile data.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyProfile() {
        User user = getCurrentUser();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    /**
     * Updates the profile of the currently authenticated user.
     * @param userUpdateDto DTO containing the updated user information.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        User user = getCurrentUser();
        userService.updateUser(user.getId(), userUpdateDto.getEmail(), userUpdateDto.getUsername(),
                userUpdateDto.getPassword());
        return ResponseEntity.ok().build();
    }

    /**
     * Unsubscribes the currently authenticated user from a specific theme.
     * @param themeId The ID of the theme to unsubscribe from.
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/me/subscriptions/{themeId}")
    public ResponseEntity<?> unsubscribeFromTheme(@PathVariable Long themeId) {
        User user = getCurrentUser();
        themeService.unsubscribe(user.getId(), themeId);
        return ResponseEntity.ok().build();
    }

  
    /**
     * Retrieves the User entity for the currently authenticated user.
     * @return The User object.
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return this.userService.findByEmail(userEmail);
    }
}

