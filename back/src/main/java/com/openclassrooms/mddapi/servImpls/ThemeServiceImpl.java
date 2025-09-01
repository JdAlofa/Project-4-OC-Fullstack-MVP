package com.openclassrooms.mddapi.servImpls;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @Override
    public Theme getTheme(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    @Override
    public void subscribe(Long userId, Long themeId) {
        User user = userRepository.findById(userId).orElse(null);
        Theme theme = themeRepository.findById(themeId).orElse(null);

        if (user != null && theme != null) {
            if (!user.getThemes().contains(theme)) {
                user.getThemes().add(theme);
                userRepository.save(user);
            }
        }
    }

    @Override
    public void unsubscribe(Long userId, Long themeId) {
        User user = userRepository.findById(userId).orElse(null);
        Theme theme = themeRepository.findById(themeId).orElse(null);

        if (user != null && theme != null) {
            user.getThemes().remove(theme);
            userRepository.save(user);
        }
    }
}
