package com.example.secondlife.domain.user.repository;

import com.example.secondlife.domain.comment.entity.QComment;
import com.example.secondlife.domain.post.entity.QPost;
import com.example.secondlife.domain.user.dto.ProfileResponse;
import com.example.secondlife.domain.user.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserQRepository {

    private final JPAQueryFactory queryFactory;

    public UserQRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public ProfileResponse findProfile(Long userId) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        JPQLQuery<Long> postCountSubQuery = JPAExpressions
                .select(post.id.count())
                .from(post)
                .where(post.user.id.eq(userId).and(post.isDeleted.eq(false)));

        JPQLQuery<Long> commentCountSubQuery = JPAExpressions
                .select(comment.id.count())
                .from(comment)
                .where(comment.user.id.eq(userId).and(comment.isDeleted.eq(false)));

        Tuple result = queryFactory
                .select(user.id, user.nickname, user.email, user.introduction.region, user.introduction.birthDate,
                        user.introduction.selfIntroduction, user.role, user.isVerified,
                        postCountSubQuery, commentCountSubQuery)
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        if (result != null) {
            return ProfileResponse.builder()
                    .userId(result.get(user.id))
                    .nickname(result.get(user.nickname))
                    .email(result.get(user.email))
                    .region(result.get(user.introduction.region))
                    .birthDate(result.get(user.introduction.birthDate))
                    .selfIntroduction(result.get(user.introduction.selfIntroduction))
                    .role(result.get(user.role))
                    .verified(Boolean.TRUE.equals(result.get(user.isVerified)))
                    .postCount(result.get(postCountSubQuery))
                    .commentCount(result.get(commentCountSubQuery))
                    .build();
        } else {
            return null;
        }
    }

}
