package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import com.example.secondlife.domain.user.valueType.Introduction;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinRequest {

    private String loginId;
    private String password;
    private String nickname;
    private String email;
    private Region region;
    private LocalDate birthDate;
    private String selfIntroduction;

    public User toEntity() {

        Introduction introduction = new Introduction(region, birthDate, selfIntroduction);

        return User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .email(email)
                .introduction(introduction)
                .role(Role.L1)
                .build();
    }
}