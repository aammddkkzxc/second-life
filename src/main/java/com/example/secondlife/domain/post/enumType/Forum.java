package com.example.secondlife.domain.post.enumType;

import java.util.List;

public enum Forum {

    FREE(List.of(Topic.DAILY_LIFE, Topic.JOB_INFO, Topic.HOBBY, Topic.ASSET)),
    BY_REGION(List.of(Topic.CLUB, Topic.STUDY));

    private List<Topic> topics;

    Forum(List<Topic> topics) {
        this.topics = topics;
    }
}