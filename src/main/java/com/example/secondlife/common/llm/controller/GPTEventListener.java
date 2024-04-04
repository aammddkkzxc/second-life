package com.example.secondlife.common.llm.controller;

import com.example.secondlife.common.llm.dto.GPTResponse;
import com.example.secondlife.common.llm.event.PostCreatedEvent;
import com.example.secondlife.common.llm.service.GPTService;
import com.example.secondlife.domain.comment.dto.CommentRequest;
import com.example.secondlife.domain.comment.dto.CommentResponse;
import com.example.secondlife.domain.comment.service.CommentService;
import com.example.secondlife.domain.post.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GPTEventListener {

    private static final Long AI1 = 3L;
    private final PostSearchService postSearchService;
    private final CommentService commentService;
    private final GPTService gptService;

    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        final CommentResponse aiCommentResponse = commentPostWithAI(event.getPostId());

        log.info("aiCommentResponse {} ", aiCommentResponse);
    }

    public CommentResponse commentPostWithAI(@PathVariable Long postId) {

        final String prompt = postSearchService.findContentsById(postId);

        final GPTResponse gptResponse = gptService.getGPTResponse(prompt);

        final String comment = gptResponse.getChoices().get(0).getMessage().getContent();

        CommentRequest request = new CommentRequest(comment);

        return commentService.save(postId, AI1, request);
    }

}