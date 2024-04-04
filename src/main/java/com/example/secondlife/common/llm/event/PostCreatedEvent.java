package com.example.secondlife.common.llm.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostCreatedEvent extends ApplicationEvent {
    private final Long postId;

    public PostCreatedEvent(Object source, Long postId) {
        super(source);
        this.postId = postId;
    }

}