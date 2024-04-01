package com.example.secondlife.common.security;

import com.example.secondlife.domain.user.entity.User;
import com.example.secondlife.domain.user.enumType.Role;
import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 사용자의 특정한 권한 return
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add((GrantedAuthority) () -> "ROLE_" + user.getRole());

        return authorities;
    }

    @Override
    public String getPassword() {
        log.info("getPassword()");
        return user.getPassword();
    }

    public Long getUserId() {
        return user.getId();
    }

    public Role getUserRole() {
        return user.getRole();
    }

    @Override
    public String getUsername() {
        log.info("getUsername() -> nickName = {}", user.getNickname());
        return user.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
