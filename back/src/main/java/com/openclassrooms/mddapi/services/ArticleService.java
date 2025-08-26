package com.openclassrooms.mddapi.services;

import java.util.List;

import com.openclassrooms.mddapi.models.Article;

public interface ArticleService {
    List<Article> getFeed(Long userId, String sortBy);
    Article getArticle (Long id);
    Article createArticle ( String title, String content, Long themeId, Long userId);

}
