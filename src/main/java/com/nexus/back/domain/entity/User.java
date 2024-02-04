package com.nexus.back.domain.entity;

import com.nexus.back.domain.enums.Role;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.validations.NonNullOrBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users", indexes = {
        @Index(name = "hash_idx", columnList = "hash", unique = true),
        @Index(name = "login_idx", columnList = "login", unique = true)
})
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NonNullOrBlank
    @Email
    @Column(unique = true)
    private String login;

    @NonNullOrBlank
    @Column(unique = true)
    private String hash;

    @NonNullOrBlank
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
