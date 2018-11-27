package com.springsecurity.core.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    private LogoutSuccessHandlerConf logoutSuccessHandlerConf;

    @Autowired
    private UserDetailsConfig userDetailsConfig;

    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/token/is-auth", "/token/register", "/token/auth").permitAll()
                    .antMatchers("/**").permitAll()
                .and()
                    .logout().clearAuthentication(Boolean.TRUE)
                    .logoutUrl("/log-out")
                    .logoutSuccessHandler(logoutSuccessHandlerConf)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Collections.singletonList("Access-Control-Allow-Origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
