package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.post.dto.PostRequest;
import com.example.secondlife.domain.post.dto.PostResponse;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
//    @Transactional(readOnly = true)
//    public void readAllWithComments(Long postId) {
//        log.info("readAllWithComments");
//
//        List<Post> allWithComments = postRepository.findAllWithComments();
//    }


    public PostResponse updatePost(Long userId, Long postId, PostRequest request) {
        log.info("updatePost");

        Post findPost = findById(postId);

        validUser(userId, findPost);

        findPost.update(request);

        return findPost.toPostResponse();
    }

    public void deletePost(Long userId, Long postId) {
        log.info("deletePost");

        Post findPost = findById(postId);

        validUser(userId, findPost);

        findPost.delete();
    }

    private void validUser(Long userId, Post findPost) {
        Long postUserId = findPost.getUser().getId();

        if (!postUserId.equals(userId)) {
            throw new AccessDeniedException("자신의 게시글만 조작할 수 있습니다.");
        }
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
