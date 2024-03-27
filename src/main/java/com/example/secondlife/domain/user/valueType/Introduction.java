package com.example.secondlife.domain.user.valueType;

import com.example.secondlife.domain.user.enumType.Region;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Introduction {

    @Enumerated(EnumType.STRING)
    private Region region;
    private LocalDateTime birthDate;
    private String selfIntroduction;

}
