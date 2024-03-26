package com.example.secondlife.domain.member.entity;

import com.example.secondlife.common.base.BaseEntity;
import com.example.secondlife.domain.member.enumType.Region;
import com.example.secondlife.domain.member.enumType.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 16, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private Region region;

    private LocalDateTime birthDate;

    private boolean isInfoPublic;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isDeleted;
}
