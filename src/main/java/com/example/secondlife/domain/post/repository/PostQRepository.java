package com.example.secondlife.domain.post.repository;

import com.example.secondlife.domain.comment.entity.QComment;
import com.example.secondlife.domain.like.post.entity.QPostLike;
import com.example.secondlife.domain.post.dto.hotPostDto;
import com.example.secondlife.domain.post.entity.QPost;
import com.example.secondlife.domain.user.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostQRepository {

    private final JPAQueryFactory queryFactory;

    public PostQRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<hotPostDto> findHotPosts(LocalDateTime startDate) {
        QPost post = QPost.post;
        QPostLike postLike = QPostLike.postLike;
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        List<Tuple> results = queryFactory
                .select(post.id, post.title, post.hits, post.createdDate,
                        postLike.id.count(), comment.id.count(), user.introduction.region)
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(postLike).on(post.id.eq(postLike.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id), comment.isDeleted.eq(false))
                .where(post.createdDate.goe(startDate).and(post.isDeleted.eq(false)))
                .groupBy(post.id, user.introduction.region)
                .orderBy(postLike.id.count().desc(), post.hits.desc())
                .limit(10)
                .fetch();

        return results.stream()
                .map(tuple -> hotPostDto.builder()
                        .postId(tuple.get(post.id))
                        .title(tuple.get(post.title))
                        .hits(Optional.ofNullable(tuple.get(post.hits)).orElse(0))
                        .region(tuple.get(user.introduction.region))
                        .likeCount(tuple.get(postLike.id.count()))
                        .createdDate(tuple.get(post.createdDate))
                        .commentCount(tuple.get(comment.id.count()))
                        .build())
                .toList();
    }

}
