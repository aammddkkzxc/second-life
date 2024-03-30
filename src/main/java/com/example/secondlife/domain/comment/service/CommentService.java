package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostSearchService postSearchService;
    private final CommentSearchService commentSearchService;
    private final UserService userService;

    public CommentResponse save(Long postId, Long userId, CommentRequest request) {
        log.info("save()");

        Post findPost = postSearchService.findById(postId);
        User findUser = userService.findById(userId);

        Comment comment = request.toEntity(findPost, findUser);

        Comment savedComment = commentRepository.save(comment);

        return savedComment.toCommentResponse();
    }

    public CommentResponse updateComment(Long commentId, Long userId, CommentRequest request) {
        log.info("updateComment()");

        Comment findComment = commentSearchService.findById(commentId);

        validUser(userId, findComment);

        findComment.update(request);

        return findComment.toCommentResponse();
    }

    public void deleteComment(Long commentId, Long userId) {
        log.info("deleteComment()");

        Comment findComment = commentSearchService.findById(commentId);

        validUser(userId, findComment);

        findComment.delete();
    }

    private void validUser(Long userId, Comment findComment) {
        log.info("validUser()");

        Long postUserId = findComment.getUser().getId();

        if (!postUserId.equals(userId)) {
            throw new AccessDeniedException("자신의 댓글만 조작할 수 있습니다.");
        }
    }
}
