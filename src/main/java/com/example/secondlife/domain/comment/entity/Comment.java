package com.example.secondlife.domain.comment.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String contents;

    private boolean isDeleted;

    public void update(CommentRequest request) {
        this.contents = request.getContents() != null ? request.getContents() : this.contents;
    }

    public void delete() {
        isDeleted = true;
    }

    @Builder
    public Comment(Post post, User user, String contents) {
        this.post = post;
        this.user = user;
        this.contents = contents;
    }

}
