package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.post.enumType.Forum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private String title;
    private String contents;
    private Forum forum;

}
