package com.example.secondlife.domain.post.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.post.dto.PostDto;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int hits;

    @Enumerated(EnumType.STRING)
    private Forum forum;

    private boolean isDeleted;

    public void update(PostDto request) {

        this.title = request.getTitle() != null ? request.getTitle() : this.title;
        this.contents = request.getContents() != null ? request.getContents() : this.contents;
        this.forum = request.getForum() != null ? request.getForum() : this.forum;

    }

    public void delete() {
        isDeleted = true;
    }

    @Builder
    public Post(User user, String title, String contents, int hits, Forum forum) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.hits = hits;
        this.forum = forum;
    }

    public void increaseViewCount() {
        ++hits;
    }
}