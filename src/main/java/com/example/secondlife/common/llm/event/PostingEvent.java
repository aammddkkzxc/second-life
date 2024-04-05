package com.example.secondlife.common.llm.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostingEvent extends ApplicationEvent {
    private final Long postId;

    public PostingEvent(Object source, Long postId) {
        super(source);
        this.postId = postId;
    }

}