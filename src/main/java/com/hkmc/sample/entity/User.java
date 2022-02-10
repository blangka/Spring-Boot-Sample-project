package com.hkmc.sample.entity;

import com.hkmc.sample.model.dto.security.ReqUser;
import com.hkmc.sample.model.enums.Authority;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String account;

    @Size(min = 0, max = 20)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Authority roles;;

    public static User createUser(ReqUser reqUser){
        User user = new User();
        user.account = reqUser.getAccount();
        user.name = reqUser.getName();
        user.password = reqUser.getPassword();
        user.email = reqUser.getEmail();
        user.phoneNumber = reqUser.getPhoneNumber();
        user.roles = Authority.ROLE_USER;
        return user;
    }

    public User toEntityAndPwdEncoder(ReqUser reqUser ,PasswordEncoder passwordEncoder){
        User user = this.createUser(reqUser);
        user.password = passwordEncoder.encode(user.password);
        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(String password) {
        return new UsernamePasswordAuthenticationToken(account, password, this.getAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.roles.toString()));
    }

    @Override
    public String getUsername() {
        return account;
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
