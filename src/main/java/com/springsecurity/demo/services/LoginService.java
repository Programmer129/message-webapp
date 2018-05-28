package com.springsecurity.demo.services;

import com.mongodb.client.gridfs.GridFSBucket;
import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserDTO;
import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Component
@Transactional
public class LoginService {

    private final UserRepository userRepository;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFSBucket bucket = (GridFSBucket) context.getBean("getFSBucket");
    private final HttpSession session;

    @Autowired
    public LoginService(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    @Transactional
    public UserLoginDTO authenticate(UserLoginDTO loginDTO) {
        User result = this.userRepository.findByUserName(loginDTO.getUserName());
        if(Objects.isNull(result) || !result.getPassword().equals(loginDTO.getPassword())) {
            return new UserLoginDTO();
        }
        this.session.setAttribute("id", loginDTO.getUserName());
        this.session.setMaxInactiveInterval(200);
        result.setIsActive(1);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setUserName(result.getUserName());
        userDTO.setPassword(result.getPassword());
        return userDTO;
    }

    @Transactional
    public Resource userImg() {
        File file = new File("/home/levani/IdeaProjects/demo/src/main/resources/profile" + session.getAttribute("id")+".png");
        Resource resource = null;
        try {
            bucket.downloadToStream("profile" + session.getAttribute("id")+".png", new FileOutputStream(file));
            FileItem fileItem = new DiskFileItem("profile.png","multipart/*", false, "profile.png",(int)file.length(), file);
            fileItem.getOutputStream();
            resource = new UrlResource(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    public UserDTO getUser() {
        UserDTO dto = new UserDTO();
        dto.setUserName(session.getAttribute("id").toString());
        return dto;
    }

    public boolean isAuthenticated() {
        return Objects.nonNull(this.session.getAttribute("id"));
    }

    @Transactional
    public void logOut() {
        User result = this.userRepository.findByUserName(session.getAttribute("id").toString());
        result.setIsActive(0);
        session.invalidate();
    }
}
