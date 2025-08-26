package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;

public interface CommentService {
    Comment createComment(String content, Long articleId, Long userId);
}
