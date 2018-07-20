package com.springsecurity;

import com.springsecurity.generator.FoodDataGenerator;
import com.springsecurity.util.FileCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

@EnableScheduling
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    @Autowired
    private FoodDataGenerator dataGenerator;

    @Autowired
    private FileCollector collector;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }

    @PostConstruct
    public void persistData() {
        dataGenerator.persistData(false);
    }

    @Transactional
    @Scheduled(cron = "0 0/1 * * * *")
    public void fileGarbageCollector() throws IOException {
        collector.collectFile();
    }
}
