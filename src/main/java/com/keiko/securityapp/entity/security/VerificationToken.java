package com.keiko.securityapp.entity.security;

import com.keiko.securityapp.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken extends BaseEntity {

    private static final int EXPIRATION = 24;

    @Column (nullable = false)
    private String token;

    @OneToOne
    private User user;
    private Timestamp expiryDate;

    public VerificationToken (String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate (EXPIRATION);
    }

    public VerificationToken (String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate (EXPIRATION);
    }

    private Timestamp calculateExpiryDate (int expiryTimeInMinutes) {
        LocalDateTime now = LocalDateTime.now ();
        now.plusHours (expiryTimeInMinutes);
        Timestamp deadLine = Timestamp.valueOf (now);
        return deadLine;
    }
}
