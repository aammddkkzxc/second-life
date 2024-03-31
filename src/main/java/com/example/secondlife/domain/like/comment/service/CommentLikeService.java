package com.example.secondlife.domain.like.comment.service;

import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.service.CommentSearchService;
import com.example.secondlife.domain.like.comment.dto.CommentLikeResponse;
import com.example.secondlife.domain.like.comment.entity.CommentLike;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
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
    private final UserService userService;

    public void saveOrDelete(Long commentId, Long userId) {
        Optional<CommentLike> findCommentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);

        if (findCommentLike.isEmpty()) {
            Comment findComment = commentSearchService.findById(commentId);
            User findUser = userService.findById(userId);

            CommentLike commentLike = CommentLike.builder()
                    .comment(findComment)
                    .user(findUser)
                    .build();

            commentLikeRepository.save(commentLike);
        } else {
            commentLikeRepository.delete(findCommentLike.get());
        }
    }

//    @Transactional(readOnly = true)
//    public Long getLikeCount(Long commentId) {
//
//        return commentLikeRepository.countByCommentId(commentId);
//    }

}
