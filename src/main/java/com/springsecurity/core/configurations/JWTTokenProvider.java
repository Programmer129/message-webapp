package com.springsecurity.core.configurations;

import com.springsecurity.core.dto.UserLoginDTO;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    private String jwtSecret = "test-password";

    private Long jwtExpired = 1000000L;

    public String generateToken(UserLoginDTO loginDTO) {

        Claims claims = Jwts.claims()
                .setSubject(loginDTO.getUserName());
        claims.put("password", String.valueOf(loginDTO.getPassword()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            logger.error("Authentication Error !");
        }

        return false;
    }
}
