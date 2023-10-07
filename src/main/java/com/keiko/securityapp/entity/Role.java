package com.keiko.securityapp.entity;

import com.keiko.securityapp.entity.listener.TimeEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class Role extends BaseEntity implements GrantedAuthority {

    @Column (nullable = false, unique = true)
    private String name;

    @ManyToMany (mappedBy = "roles", fetch = LAZY)
    private Set<User> users;

    public Role (Long id, Timestamp created, Timestamp modified, String name) {
        super (id, created, modified);
        this.name = name;
    }

    @Override
    public String getAuthority () {
        return name;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Role role = (Role) o;

        return new EqualsBuilder ()
                .append (name, role.name)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (name)
                .toHashCode ();
    }
}
