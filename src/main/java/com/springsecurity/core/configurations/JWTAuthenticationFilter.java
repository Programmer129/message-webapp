package com.springsecurity.core.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private UserDetailsConfig userDetailsConfig;

    public JWTAuthenticationFilter() {
        super("/**");
    }

    private String getTokenFrom(HttpServletRequest request) {
        String header = request.getHeader("X-Auth-Token");

        if(Objects.nonNull(header) && header.startsWith("Bearer")) {
            return header;
        }

        return null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = getTokenFrom(httpServletRequest);

        if (Objects.nonNull(token) && tokenProvider.validate(token)) {
            String userName = tokenProvider.getUserNameFromToken(token);

            UserDetails userDetails = userDetailsConfig.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
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
