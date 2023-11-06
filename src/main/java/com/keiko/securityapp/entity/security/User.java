package com.keiko.securityapp.entity.security;

import com.keiko.securityapp.entity.BaseEntity;
import com.keiko.securityapp.entity.listener.TimeEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class User extends BaseEntity {

    @Email
    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String password;

    @Transient
    @Column (nullable = false)
    private String passwordConfirm;

    private String name;

    @Column
    private boolean enabled;

    @ManyToMany (fetch = LAZY, cascade = {MERGE})
    private Set<Role> roles;

    public User (Long id, Timestamp created, Timestamp modified, @Email String email, String password, String name, Set<Role> roles) {
        super (id, created, modified);
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        User user = (User) o;

        return new EqualsBuilder ()
                .append (email, user.email)
                .append (password, user.password)
                .append (name, user.name)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (getId ())
                .append (email)
                .append (password)
                .append (name)
                .toHashCode ();
    }
}
