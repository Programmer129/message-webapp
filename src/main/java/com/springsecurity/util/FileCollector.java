package com.springsecurity.util;

import com.springsecurity.core.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileCollector {

    private static final String URL = getProjectURI();

    private final UserRepository repository;

    private final Logger logger = LoggerFactory.getLogger(FileCollector.class);

    @Autowired
    public FileCollector(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public void collectFile() {
        repository.findAllUserNames().forEach(name -> {
            Path path = Paths.get(URL + name + ".png");
            logger.info(path.toString().concat(" -> Was Not Garbage!"));
            if(Files.exists(path)) {
                logger.info(path.toString().concat(" -> Was Garbage!"));
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String getProjectURI() {
        return FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "profile";
    }
}
