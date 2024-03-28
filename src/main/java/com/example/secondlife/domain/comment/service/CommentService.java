package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.dto.CommentUpdateRequest;
import com.example.secondlife.domain.comment.dto.CommentsResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.service.PostService;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public CommentResponse save(Long postId, Long userId, CommentRequest request) {
        Post findPost = postService.findById(postId);
        User findUser = userService.findById(userId);

        Comment comment = request.toEntity(findPost, findUser);

        Comment savedComment = commentRepository.save(comment);

        return savedComment.toCommentResponse();
    }

    public CommentsResponse getComments(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentResponse> commentResponses = comments.stream()
                .map(Comment::toCommentResponse)
                .collect(Collectors.toList());

        return new CommentsResponse(commentResponses);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. commentId = " + commentId));
    }

    public CommentResponse updateComment(Long commentId, Long userId, CommentUpdateRequest request) {
        Comment comment = findCommentById(commentId);

        // 현재 사용자와 코멘트를 작성한 사용자가 다를 경우 null 로 했는데 수정 필요할듯
        if (!comment.getUser().getId().equals(userId)) {
            return null;
        }

        // 현재 사용자가 코멘트의 작성자와 동일한 경우
        comment.update(request);
        return comment.toCommentResponse();
    }
}
