package com.example.secondlife.domain.comment.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentQueryRepository;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentSearchService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    public Comment findById(Long commentId) {
        log.info("findById");

        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다. commentId = " + commentId));
    }

    public List<CommentResponse> getCommentsWithCommentLikes(Long postId) {
        log.info("getCommentsWithCommentLikes");

        return commentQueryRepository.findCommentsWithCommentLikes(postId);
    }

}