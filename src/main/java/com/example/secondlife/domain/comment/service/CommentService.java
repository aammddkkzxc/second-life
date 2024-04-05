package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentDtoUtil;
import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentSearchService commentSearchService;
    private final PostSearchService postSearchService;
    private final UserSearchService userSearchService;

    public CommentResponse save(Long postId, Long userId, CommentRequest request) {
        Post findPost = postSearchService.findById(postId);
        User findUser = userSearchService.findById(userId);

        Comment comment = request.toEntity(findPost, findUser);

        Comment savedComment = commentRepository.save(comment);

        return CommentDtoUtil.commentToCommentResponse(savedComment);
    }

    public CommentResponse update(Long commentId, Long userId, CommentRequest request) {
        Comment findComment = commentSearchService.findById(commentId);

        validUser(userId, findComment);

        findComment.update(request);

        return CommentDtoUtil.commentToCommentResponse(findComment);
    }

    public void delete(Long commentId, Long userId) {
        Comment findComment = commentSearchService.findById(commentId);

        validUser(userId, findComment);

        findComment.delete();
    }

    private void validUser(Long userId, Comment findComment) {
        Long postUserId = findComment.getUser().getId();

        if (!postUserId.equals(userId)) {
            throw new AccessDeniedException("자신의 댓글만 조작할 수 있습니다.");
        }
    }
}
