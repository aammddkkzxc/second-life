package com.example.secondlife.domain.comment.entity;

import com.example.secondlife.common.base.BaseEntity;
import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

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

    @Lob
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

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments) {

        return comments.stream()
                .map(Comment::toCommentResponse)
                .toList();
    }

    public CommentResponse toCommentResponse() {

        return CommentResponse.builder()
                .commentId(id)
                .postId(post.getId())
                .userId(user.getId())
                .nickName(user.getNickname())
                .contents(contents)
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .createdBy(getCreatedBy())
                .lastModifiedBy(getLastModifiedBy())
                .build();

    }
}
