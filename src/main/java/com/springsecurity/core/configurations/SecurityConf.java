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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
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
        http.cors();

        http.csrf().disable()
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/token/is-auth", "/token/register", "/token/auth").permitAll()
                    .antMatchers("/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and()
                    .logout()
                    .logoutUrl("/log-out")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(logoutSuccessHandlerConf);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());
    }
}
