package com.example.learning_english.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;


    public void sendMail(String email,String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean multipart = true;
        String image = "https://res.cloudinary.com/ledat123/image/upload/v1664763833/englishApp/Pink_Feminine_Thank_You_Card_1_veecdq.png";
        MimeMessageHelper helper = new MimeMessageHelper(message,multipart,"utf-8");
        String htmlMsg = "<h3>your account verification " + "code: "+ code +"</h3>" +
                "<img src="+ "'"+ image +"'"+"/>";
        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("Easy English App");
        javaMailSender.send(message);
    }
}
