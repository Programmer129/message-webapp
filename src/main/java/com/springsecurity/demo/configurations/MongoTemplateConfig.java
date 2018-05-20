package com.springsecurity.demo.configurations;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Configuration
@Component
public class MongoTemplateConfig extends AbstractMongoConfiguration {

    private static final String HOST = "0.0.0.0";
    private static final int PORT = 27017;
    private static final String USER_NAME = "root";
    private static final String DB = "admin";
    private static final String PWD = "gameri21";

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return "user";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(new ServerAddress(HOST, PORT),
                Collections.singletonList(MongoCredential.createCredential(USER_NAME, DB, PWD.toCharArray())));
    }
}
