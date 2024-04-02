package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {

    private Long userId;
    private String nickname;
    private String email;
    private Region region;
    private LocalDate birthDate;
    private String selfIntroduction;
    private Role role;
    private boolean verified;
    private Long postCount;
    private Long commentCount;

}
