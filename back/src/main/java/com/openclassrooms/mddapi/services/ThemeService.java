package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Theme;

import java.util.List;

public interface ThemeService {
    List<Theme> getAllThemes();

    Theme getTheme(Long id);

    void subscribe(Long userId, Long themeId);

    void unsubscribe(Long userId, Long themeId);
}
