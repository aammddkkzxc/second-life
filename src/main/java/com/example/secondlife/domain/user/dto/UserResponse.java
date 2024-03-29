package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.enumType.Region;
import com.example.secondlife.domain.user.enumType.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String nickname;
    private Region region;
    private LocalDate birthDate;
    private String selfIntroduction;
    private Role role;
    private boolean isDeleted;
    private LocalDateTime lastModifiedDate;

}
