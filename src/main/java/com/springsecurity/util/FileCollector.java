package com.springsecurity.util;

import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileCollector {

    private static final String URL = "/home/levani/IdeaProjects/demo/src/main/resources/profile";

    private final UserRepository repository;

    @Autowired
    public FileCollector(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void collectFile() throws IOException {
        Iterable<User> users = repository.findAll();

        for (User user : users) {
            Path path = Paths.get(URL + user.getUserName() + ".png");
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
    }
}
