package com.example.secondlife.domain.post.entity;

import com.example.secondlife.common.base.BaseEntity;
import com.example.secondlife.domain.member.entity.Member;
import com.example.secondlife.domain.post.enumType.Forum;
import com.example.secondlife.domain.post.enumType.Topic;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable= false)
    private Long id;

    @Column(nullable= false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private int hits ;

    private boolean isPublic;

    private Long reports;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}