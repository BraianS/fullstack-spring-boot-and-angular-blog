package com.github.braians.springblog.payload;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtAuthenticationResponse {

    private String username;
    private String accessToken;
    private String tokenType = "Bearer ";
    private Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtAuthenticationResponse(
    String accessToken,
    String username,
    Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.accessToken = accessToken;
        this.authorities = authorities;
    }

    
}
