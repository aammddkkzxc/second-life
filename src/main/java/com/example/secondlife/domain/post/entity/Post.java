package com.example.secondlife.domain.post.entity;

import com.example.secondlife.common.base.BaseTimeEntity;
import com.example.secondlife.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable= false)
    private Long id;

    @Column(name="title", nullable= false)
    private String title;

    @Column(name="contents", nullable = false)
    private String contents;

    private Long hit;

    private boolean isPublic;

    private Long reports;

    @Enumerated(EnumType.STRING)
    private String forum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}