package com.example.secondlife.domain.like.comment.service;

import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.like.comment.dto.CommentLikeDtoUtil;
import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.entity.CommentLike;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentSearchService commentSearchService;
    private final UserSearchService userSearchService;

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
        Comment findComment = commentSearchService.findById(commentId);
        User findUser = userSearchService.findById(userId);

        CommentLike commentLike = CommentLike.builder()
                .comment(findComment)
                .user(findUser)
                .build();

        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);

        return CommentLikeDtoUtil.commentLikeToCommentLikeResponse(savedCommentLike);
    }

    public void delete(Long commentId, Long userId) {
        commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .ifPresent(commentLikeRepository::delete);
    }

}
