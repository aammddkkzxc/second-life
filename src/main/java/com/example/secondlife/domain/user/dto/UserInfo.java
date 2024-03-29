package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.enumType.Region;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    private String nickname;
    private Region region;
    private LocalDate birthDate;
    private String selfIntroduction;
    private boolean isDeleted;

}
