package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.comment.entity.QComment;
import com.example.secondlife.domain.like.post.entity.QPostLike;
import com.example.secondlife.domain.post.dto.HotPostDto;
import com.example.secondlife.domain.post.entity.QPost;
import com.example.secondlife.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostQRepository {

    private final JPAQueryFactory queryFactory;

    public PostQRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<HotPostDto> findHotPosts(LocalDateTime startDate) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        List<HotPostDto> posts = queryFactory
                .select(Projections.constructor(HotPostDto.class,
                        post.id,
                        post.title,
                        post.hits,
                        post.createdDate,
                        Expressions.constant(0L),
                        Expressions.constant(0L),
                        user.introduction.region))
                .from(post)
                .leftJoin(post.user, user)
                .where(post.createdDate.goe(startDate).and(post.isDeleted.eq(false)))
                .groupBy(post.id, user.introduction.region)
                .orderBy(post.hits.desc())
                .limit(10)
                .fetch();

        posts.forEach(p -> {
            Long likeCount = queryFactory
                    .select(postLike.id.count())
                    .from(postLike)
                    .where(postLike.post.id.eq(p.getPostId()))
                    .fetchOne();

            Long commentCount = queryFactory
                    .select(comment.id.count())
                    .from(comment)
                    .where(comment.post.id.eq(p.getPostId()), comment.isDeleted.eq(false))
                    .fetchOne();

            p.setLikeCount(likeCount);
            p.setCommentCount(commentCount);
        });

        return posts;
    }

}
