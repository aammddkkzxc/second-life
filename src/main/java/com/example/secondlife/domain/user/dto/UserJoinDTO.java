package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.valueType.Introduction;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserJoinDTO {

    private String loginId;

    private String password;

    private String nickname;

    private String email;

    @Embedded
    private Introduction introduction;

    private boolean isIntroPublic;

    @Enumerated(EnumType.STRING)
    private Region region;
}
