package com.example.secondlife.domain.comment.service;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.comment.repository.CommentRepository;
import com.example.secondlife.domain.like.comment.dto.CommentLikeCountDto;
import com.example.secondlife.domain.like.comment.repository.CommentLikeRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final CommentLikeRepository commentLikeRepository;

    public Comment findById(Long commentId) {
        log.info("findById");

        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. commentId = " + commentId));
    }

    public List<CommentResponse> getCommentsWithCommentLikes(Long postId) {
        log.info("getCommentsWithCommentLikes");

        List<Comment> comments = commentRepository.findByPostIdAndIsDeletedFalse(postId);

        List<CommentResponse> commentResponseList = Comment.toCommentResponseList(comments);

        List<CommentLikeCountDto> commentLikeCountDtos = commentLikeRepository.countLikesByPostIdGroupByCommentId(
                postId);

        Map<Long, Long> likeCountMap = commentLikeCountDtos.stream()
                .collect(Collectors.toMap(CommentLikeCountDto::getCommentId, CommentLikeCountDto::getLikeCount));

        commentResponseList.forEach(commentResponse -> {
            Long likeCount = likeCountMap.getOrDefault(commentResponse.getCommentId(), 0L);
            commentResponse.setLikeCount(likeCount);
        });

        return commentResponseList;
    }

    public Long getCommentCount(Long userId) {
        log.info("getPostCount");

        return commentRepository.countByUserId(userId);
    }

}