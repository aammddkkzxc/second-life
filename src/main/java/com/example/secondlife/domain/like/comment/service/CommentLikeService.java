package com.example.secondlife.domain.like.comment.service;

import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.service.CommentService;
import com.example.secondlife.domain.like.comment.dto.CommentLikeCountResponse;
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
    private final CommentService commentService;
    private final UserService userService;

    public CommentLikeResponse save(Long commentId, Long userId) {
        Comment findComment = commentService.findById(commentId);
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
    public CommentLikeCountResponse getLikeCount(Long commentId) {

        Long likeCount = commentLikeRepository.countByCommentId(commentId);

        return CommentLikeCountResponse.builder()
                .count(likeCount)
                .commentId(commentId)
                .build();
    }

    public void delete(Long commentId, Long userId) {
        commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .ifPresent(commentLikeRepository::delete);
    }
}
