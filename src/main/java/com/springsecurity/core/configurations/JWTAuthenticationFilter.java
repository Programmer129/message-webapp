package com.springsecurity.core.configurations;

import com.springsecurity.core.exceptions.UserNotAuthenticatedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JWTTokenProvider tokenProvider;

    @Autowired
    public JWTAuthenticationFilter(JWTTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws UserNotAuthenticatedException, IOException, ServletException {
        String requestHeader = request.getHeader("Authorisation");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(request.getHeader(headerNames.nextElement()));
        }
        if(request.getRequestURL().toString().contains("token")) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("Token ")) {
            authToken = requestHeader.substring(6);
            try {
                username = tokenProvider.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            } catch (SignatureException e) {
                logger.error("Invalid token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        if (username != null && SecurityContextHolder.getContext() != null) {

           if(!tokenProvider.validate(authToken)) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
               return;
           }
        }

        chain.doFilter(request, response);
    }
}
