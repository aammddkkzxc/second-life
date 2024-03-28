package com.example.secondlife.Post_like.service;

import com.example.secondlife.Post_like.dto.PostLikeRequest;
import com.example.secondlife.Post_like.repository.PostLikeRepository;
import com.example.secondlife.domain.likes.post.entity.PostLikes;
import com.example.secondlife.domain.post.entity.Post;
import com.example.secondlife.domain.post.repository.PostRepository;
import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void insert(PostLikeRequest postLikeRequest) throws Exception {
        User user = userRepository.findById(postLikeRequest.getMember_id())
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        Post post = postRepository.findById(postLikeRequest.getPost_id())
                .orElseThrow(() -> new NotFoundException("Post Not Found"));

    }

    PostLikes postLikes = PostLikes.builder()
            .post(post)
            .user(member)
            .build();

    postLikesRepository.save(postLikes);
    public void delete(PostLikeRequest postLikeRequest) {
        boardRepository.delete(postLikeRequest.toEntity());
    }
}
