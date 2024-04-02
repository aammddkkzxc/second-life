package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.post.enumType.Forum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostDto {

    private String title;
    private String contents;
    private Forum forum;

}
