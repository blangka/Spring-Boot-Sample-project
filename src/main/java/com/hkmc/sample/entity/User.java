package com.hkmc.sample.entity;

import com.hkmc.sample.model.dto.security.ReqUser;
import com.hkmc.sample.model.enums.Authority;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String account;

    @Size(min = 0, max = 20)
    private String name;

    private String password;

    @Email
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public static User createUser(ReqUser reqUser){
        User user = new User();
        user.account = reqUser.getAccount();
        user.name = reqUser.getName();
        user.password = reqUser.getPassword();
        user.email = reqUser.getEmail();
        user.phoneNumber = reqUser.getPhoneNumber();
        user.authority = reqUser.getAuthority();

        return user;
    }

    public User toEntityAndPwdEncoder(ReqUser reqUser ,PasswordEncoder passwordEncoder){
        User user = this.createUser(reqUser);
        user.password = passwordEncoder.encode(user.password);
        return user;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.toString());

        return new UsernamePasswordAuthenticationToken(email, password, Collections.singleton(grantedAuthority));
    }
}
