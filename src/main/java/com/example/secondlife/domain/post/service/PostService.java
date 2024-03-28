package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.dto.PostUpdateRequest;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostResponse save(Long userId, PostRequest request) {
        log.info("save");

        Post savedPost = postRepository.save(postRequestToPost(userId, request));

        return savedPost.toPostResponse();
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        log.info("findById");

        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. postId = " + postId));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        log.info("getPosts");

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(Post::toPostResponse);
    }

    //TODO : Comment class 생성 후 수정
    @Transactional(readOnly = true)
    public void readAllWithComments(Long postId) {
        log.info("readAllWithComments");

        List<Post> allWithComments = postRepository.findAllWithComments();
    }

    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        log.info("updatePost");

        Post findPost = findById(postId);

        findPost.update(request);

        return findPost.toPostResponse();
    }

    public void deletePost(Long postId) {
        log.info("deletePost");

        Post findPost = findById(postId);

        findPost.delete();
    }

    private Post postRequestToPost(Long userId, PostRequest request) {

        User findUser = userService.findById(userId);

        return Post.builder()
                .user(findUser)
                .title(request.getTitle())
                .contents(request.getContents())
                .isPublic(request.isPublic())
                .forum(request.getForum())
                .build();

    }
}
