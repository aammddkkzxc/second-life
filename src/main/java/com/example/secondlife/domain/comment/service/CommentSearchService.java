package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentSearchService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. commentId = " + commentId));
    }

    public List<CommentResponse> getCommentsWithCommentLikes(Long postId) {
        List<CommentResponse> commentResponses = getComments(postId);

        for (CommentResponse commentResponse : commentResponses) {
            Long commentId = commentResponse.getCommentId();
            Long likeCount = commentLikeRepository.countByCommentId(commentId);
            commentResponse.setLikeCount(likeCount);
        }

        return commentResponses;
    }

    private List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndIsDeletedFalse(postId);

        return Comment.toCommentResponseList(comments);
    }
}
