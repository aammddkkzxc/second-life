package com.example.secondlife.domain.post.dto;

import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.post.entity.Post;
import java.util.List;

public class PostDtoUtil {

    public static PostResponse postToPostResponse(Post post) {
        return PostResponse.builder() //TODO user를 Lazy로 가져오는 것을 해결해야함
                .userId(post.getUser().getId())
                .postId(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .hits(post.getHits())
                .postUserNickname(post.getUser().getNickname())
                .createdDate(post.getCreatedDate())
                .lastModifiedDate(post.getLastModifiedDate())
                .build();
    }

    public static PostResponse postWithCommentResponseToPostResponse(Post post, List<CommentResponse> comments) {

        PostResponse postResponse = postToPostResponse(post);
        postResponse.setCommentResponses(comments);

        return postResponse;
    }

}
