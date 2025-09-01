package com.openclassrooms.mddapi.servImpls;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
            ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    @Override
    public List<Article> getFeed(Long userId, String sortBy) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return List.of();
        }
        if ("asc".equalsIgnoreCase(sortBy)) {
            return articleRepository.findAllByThemeInOrderByCreatedAtAsc(user.getThemes());
        }
        return articleRepository.findAllByThemeInOrderByCreatedAtDesc(user.getThemes());
    }

    @Override
    public Article getArticle(Long id) {
        return this.articleRepository.findByIdWithComments(id).orElse(null);
    }

    @Override
    public Article createArticle(String title, String content, Long themeId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Theme theme = themeRepository.findById(themeId).orElse(null);

        if (user == null || theme == null) {
            throw new RuntimeException("User or Theme not found");
        }
        Article article = new Article()
                .setTitle(title)
                .setContent(content)
                .setUser(user)
                .setTheme(theme);

        return articleRepository.save(article);
    }
}