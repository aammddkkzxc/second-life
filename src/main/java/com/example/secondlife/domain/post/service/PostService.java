//package com.example.secondlife.domain.post.service;
//
//import com.example.secondlife.common.wrapper.ApiResponse;
//import com.example.secondlife.domain.comment.dto.CommentResponse;
//import com.example.secondlife.domain.post.dto.PostResponse;
//import com.example.secondlife.domain.post.dto.PostingRequest;
//import com.example.secondlife.domain.post.entity.Post;
//import com.example.secondlife.domain.post.repository.PostRepository;
//import com.example.secondlife.domain.user.entity.User;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    public PostResponse create(PostingRequest request) {
//        log.info("createPost");
//
//        Post savedPost = postRepository.save(postingRequestToPost(request));
//
//        return PostResponse.from(savedPost);
//    }
//
//    public void readAllWithComments() {
//        log.info("readAllWithComments");
//
//        List<Post> allWithComments = postRepository.findAllWithComments();
//    }
//
//    public void updatePost() {
//        log.info("updatePost");
//    }
//
//    public void deletePost() {
//        log.info("deletePost");
//    }
//
//    private Post postingRequestToPost(PostingRequest request) {
//        Long userId = request.getUserId();
//        User findUser = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 id : + " + userId + " 의 사용자가 존재하지 않습니다."));
//
//        return Post.builder()
//                .user(findUser)
//                .title(request.getTitle())
//                .contents(request.getContents())
//                .isPublic(request.isPublic())
//                .forum(request.getForum())
//                .build();
//    }
//
//    private PostResponse articleToArticleResponseWithComments(Article article) {
//
//        log.info("articleToArticleResponse()");
//
//        List<CommentResponse> commentResponses = article
//                .getComments()
//                .stream()
//                .map(CommentResponse::from)
//                .toList();
//
//        ApiResponse<List<CommentResponse>> response = new ApiResponse<>(article.getComments().size(), commentResponses);
//
//        return ArticleResponseWithComments.builder()
//                .articleId(article.getId())
//                .title(article.getTitle())
//                .content(article.getContent())
//                .createdAt(article.getCreatedAt())
//                .updatedAt(article.getUpdatedAt())
//                .comments(response)
//                .build();
//
//    }
//
//}
