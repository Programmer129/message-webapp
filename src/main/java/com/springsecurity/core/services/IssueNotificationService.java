package com.springsecurity.core.services;

import com.springsecurity.core.dto.MailSendDTO;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class IssueNotificationService {

    public MailSendDTO sendMail(MailSendDTO sendDTO) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", sendDTO.getHost());
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        String username = sendDTO.getSender();
        String password = sendDTO.getPassword();
        Authenticator authenticator = new Authenticator() {
            private PasswordAuthentication pa = new PasswordAuthentication(username, password);

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return pa;
            }
        };

        Session session = Session.getDefaultInstance(properties, authenticator);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendDTO.getSender()));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendDTO.getRecipient()));

        message.setSubject(sendDTO.getSubject());

        message.setText(sendDTO.getText());

        Transport.send(message);

        return sendDTO;
    }

}
