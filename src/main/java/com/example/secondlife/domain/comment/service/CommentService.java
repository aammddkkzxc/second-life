package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
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
}
