package com.example.secondlife.domain.comment.dto;

import com.example.secondlife.domain.comment.entity.Comment;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String contents;

    public Comment toEntity(Post post, User user) {

        return Comment.builder()
                .post(post)
                .user(user)
                .contents(contents)
                .build();

    }
}
