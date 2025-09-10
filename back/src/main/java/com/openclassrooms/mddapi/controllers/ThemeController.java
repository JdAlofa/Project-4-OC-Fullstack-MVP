package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/themes")
@Slf4j
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

    @GetMapping("/subscriptions")
    public ResponseEntity<List<ThemeDto>> getSubscribedThemes() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = this.userService.findByEmail(userEmail);

            if (user == null) {
                return ResponseEntity.status(401).build();
            }

            List<ThemeDto> subscribedThemes = user.getThemes().stream()
                    .map(themeMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(subscribedThemes);
        } catch (Exception e) {
            log.error("Error fetching the subscribed themes", e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/{themeId}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("themeId") Long themeId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            User user = this.userService.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(401).body("User not found");
            }

            themeService.subscribe(user.getId(), themeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error subscribing to theme", e);
            return ResponseEntity.badRequest().body("Error subscribing to theme");
        }
    }

}
