package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.post.enumType.Forum;
import lombok.Data;

@Data
public class PostingRequest {

    private Long userId;
    private String title;
    private String contents;
    private boolean isPublic;
    private Forum forum;

}
