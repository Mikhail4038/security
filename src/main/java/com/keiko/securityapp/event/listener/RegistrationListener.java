package com.keiko.securityapp.event.listener;

import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.entity.security.VerificationToken;
import com.keiko.securityapp.event.OnRegistrationCompleteEvent;
import com.keiko.securityapp.service.security.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener
        implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent (OnRegistrationCompleteEvent event) {
        User user = event.getUser ();

        VerificationToken verificationToken = createVerificationToken (user);
        sendEmail (user, verificationToken);
    }

    private VerificationToken createVerificationToken (User user) {
        final String token = UUID.randomUUID ().toString ();
        VerificationToken verificationToken = new VerificationToken (token, user);
        tokenService.createToken (verificationToken);
        return verificationToken;
    }

    private void sendEmail (User user, VerificationToken verificationToken) {
        String recipientAddress = user.getEmail ();
        String subject = "Registration Confirmation";
        String token = verificationToken.getToken ();
        final String message = String.format (
                "You registered successfully. To confirm your registration, please use token: %s", token);

        final SimpleMailMessage email = new SimpleMailMessage ();
        email.setTo (recipientAddress);
        email.setSubject (subject);
        email.setText (message);
        email.setFrom (env.getProperty ("support.email"));

        javaMailSender.send (email);
    }
}
