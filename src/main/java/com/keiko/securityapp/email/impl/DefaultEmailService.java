package com.keiko.securityapp.email.impl;

import com.keiko.securityapp.email.EmailService;
import com.keiko.securityapp.entity.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class DefaultEmailService implements EmailService {

    @Value ("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail (EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage ();
        simpleMailMessage.setFrom (sender);
        simpleMailMessage.setText (emailDetails.getBody ());
        simpleMailMessage.setTo (emailDetails.getRecipient ());
        javaMailSender.send (simpleMailMessage);
    }
}
