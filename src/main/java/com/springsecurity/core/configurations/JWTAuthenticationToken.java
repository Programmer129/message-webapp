package com.springsecurity.core.configurations;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JWTAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }
}
