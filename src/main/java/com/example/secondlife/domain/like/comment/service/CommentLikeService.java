package com.example.secondlife.domain.like.comment.service;

import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.entity.CommentLike;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentSearchService commentSearchService;
    private final UserService userService;

    public CommentLikeResponse save(Long commentId, Long userId) {
        Comment findComment = commentSearchService.findById(commentId);
        User findUser = userService.findById(userId);

        CommentLike commentLike = CommentLike.builder()
                .comment(findComment)
                .user(findUser)
                .build();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        return CommentLikeResponse.builder()
                .commentId(savedCommentLike.getComment().getId())
                .userId(savedCommentLike.getUser().getId())
                .commentLikeId(savedCommentLike.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public Long getLikeCount(Long commentId) {

        return commentLikeRepository.countByCommentId(commentId);
    }

    public void delete(Long commentId, Long userId) {
        commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .ifPresent(commentLikeRepository::delete);
    }
}
