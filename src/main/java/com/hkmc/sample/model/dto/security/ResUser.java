package com.hkmc.sample.model.dto.security;

import com.hkmc.sample.model.enums.Authority;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ResUser {

    @NotEmpty
    private String account;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Email
    private String email;

    @NumberFormat
    private String phoneNumber;

    @NotEmpty
    private Authority authority;
}
