package com.example.secondlife.domain.comment.repository;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.entity.QComment;
import com.example.secondlife.domain.like.comment.entity.QCommentLike;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CommentQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<CommentResponse> findCommentsWithCommentLikes(Long postId) {
        QComment comment = QComment.comment;
        QCommentLike commentLike = QCommentLike.commentLike;

        return queryFactory
                .select(Projections.constructor(CommentResponse.class,
                        comment.id,
                        comment.post.id,
                        comment.user.id,
                        comment.user.nickname,
                        comment.contents,
                        comment.createdDate,
                        comment.lastModifiedDate,
                        commentLike.comment.id.countDistinct()))
                .from(comment)
                .leftJoin(commentLike).on(commentLike.comment.id.eq(comment.id))
                .where(comment.post.id.eq(postId).and(comment.isDeleted.eq(false)))
                .groupBy(comment.id, comment.post.id, comment.user.id, comment.user.nickname, comment.contents,
                        comment.createdDate, comment.lastModifiedDate)
                .fetch();

    }

}
