package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.entity.Post;
import java.util.List;

public class PostDtoUtil {

    public static PostResponse postToPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .hits(post.getHits())
                .createdDate(post.getCreatedDate())
                .lastModifiedDate(post.getLastModifiedDate())
                .lastContentUpdate(post.getLastContentUpdate())
                .forum(post.getForum())
                .build();
    }

    public static PostResponse postWithCommentResponseToPostResponse(Post post, List<CommentResponse> comments) {

        PostResponse postResponse = postToPostResponse(post);
        postResponse.setCommentResponses(comments);

        return postResponse;
    }

}
