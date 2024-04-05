package com.example.secondlife.domain.user.dto;

import com.example.secondlife.domain.user.enumType.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateUserRoleRequest {

    private Role role;

}
