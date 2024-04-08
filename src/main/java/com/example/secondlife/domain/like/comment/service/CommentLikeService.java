package com.example.secondlife.domain.like.comment.service;

import com.example.secondlife.domain.like.comment.dto.CommentLikeDtoUtil;
import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.entity.CommentLike;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public Optional<CommentLikeResponse> saveOrDelete(Long commentId, Long userId) {
        Optional<CommentLike> findCommentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        if (findCommentLike.isEmpty()) {
            CommentLikeResponse commentLikeResponse = save(commentId, userId);

            return Optional.of(commentLikeResponse);
        } else {
            delete(commentId, userId);

            return Optional.empty();
        }
    }

    public CommentLikeResponse save(Long commentId, Long userId) {
        CommentLike commentLike = CommentLike.builder()
                .commentId(commentId)
                .userId(userId)
                .build();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        return CommentLikeDtoUtil.commentLikeToCommentLikeResponse(savedCommentLike);
    }

    public void delete(Long commentId, Long userId) {
        commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .ifPresent(commentLikeRepository::delete);
    }

}
