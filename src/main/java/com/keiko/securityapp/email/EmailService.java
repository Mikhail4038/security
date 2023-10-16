package com.keiko.securityapp.email;

import com.keiko.securityapp.entity.email.EmailDetails;

public interface EmailService {

    void sendEmail (EmailDetails emailDetails);
}
