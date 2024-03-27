package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private Long userId;
    private Long postId;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;


    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getUser().getId(),
                post.getId(),
                post.getContents(),
                post.getCreatedDate(),
                post.getLastModifiedDate(),
                post.getCreatedBy(),
                post.getLastModifiedBy()
        );

    }

}
