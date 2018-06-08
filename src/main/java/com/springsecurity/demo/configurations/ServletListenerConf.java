package com.springsecurity.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.Transactional;

@Component
@Configuration
public class ServletListenerConf implements ServletContextListener {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    @Transactional
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Query query = manager.createNativeQuery("update user set is_active=0");
        query.executeUpdate();
    }
}
