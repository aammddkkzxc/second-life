package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.dto.CommentingRequest;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentResponse addComment(Long userId, Long postId, CommentingRequest request) {
        Post post = postRepository.findById(postId);
    }
}
