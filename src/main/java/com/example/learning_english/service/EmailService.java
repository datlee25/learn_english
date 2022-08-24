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
        MimeMessageHelper helper = new MimeMessageHelper(message,multipart,"utf-8");
        String htmlMsg = "<h3>Im testing send a HTML email</h3>";
        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("Test send HTML email" + "code: "+ code);
        javaMailSender.send(message);
    }
}
