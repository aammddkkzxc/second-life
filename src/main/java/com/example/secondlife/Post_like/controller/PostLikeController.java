package com.example.secondlife.Post_like.controller;


import static jdk.vm.ci.hotspot.HotSpotCompilationRequestResult.success;

import com.example.secondlife.Post_like.dto.PostLikeRequest;
import com.example.secondlife.Post_like.service.PostLikeService;
import com.example.secondlife.domain.likes.post.entity.PostLikes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@XSlf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/likes/PostLikes")
public class PostLikeController extends PostLikes {

    private final PostLikeService postlikeService;

    @PostMapping
    public ResponseResult<?> insert(@RequestBody @Valid PostLikeRequest postLikeRequest) {
        PostLikeService.insert(postLikeRequest);
        return success(null);

    }

    @DeleteMapping
    public ResponseResult<?> delete(@RequestBody @Valid PostLikeRequest postLikeRequest) {
        boardService.delete(postLikeRequest);
        return success(null);
    }
}