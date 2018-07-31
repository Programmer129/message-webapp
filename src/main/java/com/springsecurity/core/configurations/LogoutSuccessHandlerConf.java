package com.springsecurity.core.configurations;

import com.springsecurity.core.exceptions.UserNotAuthenticatedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Configuration
public class LogoutSuccessHandlerConf extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication)
        throws UserNotAuthenticatedException {
        String name = Optional
                        .ofNullable(authentication)
                        .map(Principal::getName)
                        .orElseThrow(UserNotAuthenticatedException::new);

        entityManager
                .createQuery("UPDATE User u SET u.isActive=0 WHERE u.userName=:name")
                .setParameter("name", name)
                .executeUpdate();
    }
}
