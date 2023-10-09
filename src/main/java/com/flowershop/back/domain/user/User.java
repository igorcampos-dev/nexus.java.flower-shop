package com.flowershop.back.domain.user;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String hash;
    private String login;
    private String password;
    public User(String login, String password, Role role, StatusUser status, String hash){
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
        this.hash = hash;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if (this.role.toString() == "ADMIN") return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
       else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
