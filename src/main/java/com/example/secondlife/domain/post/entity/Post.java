package com.example.secondlife.domain.post.entity;

import com.example.secondlife.common.base.BaseEntity;
import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
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
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
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

    @Enumerated(EnumType.STRING)
    private Forum forum;

    private boolean isDeleted;

    public void update(PostRequest request) {

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

    public PostResponse toPostResponse() {

        return PostResponse.builder()
                .userId(user.getId())
                .postId(id)
                .title(title)
                .contents(contents)
                .hits(hits)
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .createdBy(getCreatedBy())
                .lastModifiedBy(getLastModifiedBy())
                .build();

    }

}