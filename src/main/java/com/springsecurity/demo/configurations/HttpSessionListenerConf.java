package com.springsecurity.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            Files.delete(Paths.get("/home/levani/IdeaProjects/demo/src/main/resources/profile" + httpSessionEvent.getSession().getAttribute("id")+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Query query = manager.createNativeQuery("update user set is_active=0 where user_name=:name");
        query.setParameter("name", httpSessionEvent.getSession().getAttribute("id").toString());
        query.executeUpdate();
    }
}
