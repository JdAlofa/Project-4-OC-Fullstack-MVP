package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByThemeInOrderByCreatedAtDesc(List<Theme> themes);

    List<Article> findAllByThemeInOrderByCreatedAtAsc(List<Theme> themes);

    /**
     * Finds an article by its ID and eagerly fetches its associated comments.
     * This prevents LazyInitializationException.
     * 
     * @param id The ID of the article.
     * @return An Optional containing the Article with its comments.
     */
    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.comments WHERE a.id = :id")
    Optional<Article> findByIdWithComments(@Param("id") Long id);
}
