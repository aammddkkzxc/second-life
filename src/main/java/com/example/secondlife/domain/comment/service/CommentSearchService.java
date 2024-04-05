package com.example.secondlife.domain.comment.service;

import com.example.secondlife.common.exception.NotFoundException;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentQueryRepository;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentSearchService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글이 존재하지 않습니다. commentId = " + commentId));
    }

    public List<CommentResponse> getCommentsWithCommentLikes(Long postId) {
        return commentQueryRepository.findCommentsWithCommentLikes(postId);
    }

}