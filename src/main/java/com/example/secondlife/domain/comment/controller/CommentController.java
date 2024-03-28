package com.example.secondlife.domain.comment.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.dto.CommentingRequest;
import com.example.secondlife.domain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId, Long userId, CommentingRequest request) {
        CommentResponse commentResponse = commentService.addComment(postId, userId,request);

        return ResponseEntity.status(CREATED).body(commentResponse);
    }
}
