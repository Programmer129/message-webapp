package com.springsecurity.core.configurations;

import com.springsecurity.core.dto.UserLoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    private String jwtSecret = "test-password";

    public String generateToken(UserLoginDTO loginDTO) {

        Claims claims = Jwts.claims()
                .setSubject(loginDTO.getUserName());
        claims.put("userName", String.valueOf(loginDTO.getUserName()));

        long jwtExpired = 1000000L;
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + jwtExpired))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    Boolean validate(String token) {
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
