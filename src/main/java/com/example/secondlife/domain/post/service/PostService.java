package com.example.secondlife.domain.post.service;

import com.example.secondlife.domain.post.dto.PostingRequest;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(PostingRequest request) {
        log.info("createPost");
        postRepository.save(request.toEntity());
    }

    public void readPost() {
        log.info("readPost");
    }

    public void updatePost() {
        log.info("updatePost");
    }

    public void deletePost() {
        log.info("deletePost");
    }

    private Post postingRequestToPost(PostingRequest request) {
        Long userId = request.getUserId();
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id : + " + userId + " 의 사용자가 존재하지 않습니다."));

        return Post.builder()
                .user(findUser)
                .title(request.getTitle())
                .contents(request.getContents())
                .isPublic(request.isPublic())
                .forum(request.getForum())
                .build();
    }

}
