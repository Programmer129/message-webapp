package com.springsecurity.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsConfig userDetailsConfig;

    @Autowired
    private LogoutSuccessHandlerConf logoutSuccessHandlerConf;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                    .antMatchers("/api/is-auth", "/api/register").permitAll()
                    .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                .and()
                    .logout()
                    .logoutUrl("/log-out")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(logoutSuccessHandlerConf)
                .and()
                    .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsConfig)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
