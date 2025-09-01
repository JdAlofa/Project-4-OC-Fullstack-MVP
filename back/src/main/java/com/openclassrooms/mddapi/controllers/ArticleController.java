package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.CreateArticleDto;
import com.openclassrooms.mddapi.dto.CreateCommentDto;
import com.openclassrooms.mddapi.mappers.ArticleMapper;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    public ArticleController(ArticleService articleService, CommentService commentService, UserService userService,
            ArticleMapper articleMapper, CommentMapper commentMapper) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getFeed() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);

        List<Article> feed = articleService.getFeed(user.getId(), "desc");
        List<ArticleDto> feedDtos = feed.stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
        Article article = articleService.getArticle(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleMapper.toDto(article));
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody CreateArticleDto createArticleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);

        Article newArticle = articleService.createArticle(
                createArticleDto.getTitle(),
                createArticleDto.getContent(),
                createArticleDto.getThemeId(),
                user.getId());

        ArticleDto responseDto = articleMapper.toDto(newArticle);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long id,
            @Valid @RequestBody CreateCommentDto createCommentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);

        Comment newComment = commentService.createComment(createCommentDto.getContent(), id, user.getId());
        return ResponseEntity.ok(commentMapper.toDto(newComment));
    }
}
