package com.springsecurity.demo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
@Configuration
public class ServletListenerConf implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
