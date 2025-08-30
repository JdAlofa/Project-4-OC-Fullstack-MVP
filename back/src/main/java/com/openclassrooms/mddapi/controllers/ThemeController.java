package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.mappers.ThemeMapper;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {
    private final ThemeService themeService;
    private final ThemeMapper themeMapper;
    private final UserService userService;

    public ThemeController(ThemeService themeService, ThemeMapper themeMapper, UserService userService) {
        this.userService = userService;
        this.themeMapper = themeMapper;
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeDto>> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();
        List<ThemeDto> themeDtos = themes.stream()
                .map(themeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(themeDtos);
    }

     @PostMapping("/{themeId}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("themeId") Long themeId) {
        try {
            // Get the email of the logged-in user from the security context
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = userDetails.getUsername();

            // Find the user by email to get their ID
            User user = this.userService.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(401).body("User not found");
            }

            themeService.subscribe(user.getId(), themeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error subscribing to theme");
        }
    }

    @DeleteMapping("/{themeId}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("themeId") Long themeId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userEmail = userDetails.getUsername();

            User user = this.userService.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(401).body("User not found");
            }

            themeService.unsubscribe(user.getId(), themeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error unsubscribing from theme");
        }
    }
}
