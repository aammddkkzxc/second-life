package com.example.secondlife.domain.post.entity;

import com.example.secondlife.common.base.BaseEntity;
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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private int hits;

    private boolean isPublic;

    @Enumerated(EnumType.STRING)
    private Forum forum;


    @Builder
    public Post(User user, String title, String contents, boolean isPublic, Forum forum) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.isPublic = isPublic;
        this.forum = forum;
    }

}