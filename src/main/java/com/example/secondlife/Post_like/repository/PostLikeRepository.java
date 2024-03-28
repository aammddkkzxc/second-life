package com.example.secondlife.Post_like.repository;

import com.example.secondlife.domain.post.entity.Post;

public class PostLikeRepository {
    @Override
    public void updateCount(Post post1, boolean b) {
        if (b) {
            queryFactory.update(post)
                    .set(post.likeCount, post.likeCount.add(1))
                    .where(post.id.eq(post1.getId()))
                    .execute();
        } else {
            queryFactory.update(post)
                    .set(post.likeCount, post.likeCount.subtract(1))
                    .where(post.id.eq(post1.getId()))
                    .execute();
        }
    }
}
