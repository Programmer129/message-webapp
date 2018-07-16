package com.springsecurity.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.transaction.Transactional;

@Component
@Configuration
public class HttpSessionListenerConf implements HttpSessionListener {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    @Transactional
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Query query = manager.createNativeQuery("update user set is_active=0 where user_name=:name");
        query.setParameter("name", httpSessionEvent.getSession().getAttribute("id").toString());
        query.executeUpdate();
    }
}
