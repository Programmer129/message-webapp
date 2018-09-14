package com.springsecurity;

import com.springsecurity.generator.FoodDataGenerator;
import com.springsecurity.util.FileCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@EnableScheduling
@SpringBootApplication
public class MessageApplication {

    @Autowired
    private FoodDataGenerator dataGenerator;

    @Autowired
    private FileCollector collector;

    public static void main(String[] args) {
        SpringApplication.run(MessageApplication.class, args);
    }

    @Bean()
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void persistData() {
        dataGenerator.persistData(false);
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0/30 * * * *")
    public void fileGarbageCollector() {
        collector.collectFile();
    }
}
