package com.springsecurity.core.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JWTTokenProvider tokenProvider;

    public JWTAuthenticationFilter() {
        super("/api/**");
    }

    private String getTokenFrom(HttpServletRequest request) {
        String header = request.getHeader("Authorisation");

        if(Objects.nonNull(header) && header.startsWith("Token")) {
            return header.substring(6, header.length());
        }

        return null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = getTokenFrom(httpServletRequest);

        if (Objects.nonNull(token) && tokenProvider.validate(token)) {
            return getAuthenticationManager().authenticate(new JWTAuthenticationToken(token));
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
