package com.hkmc.sample.model.dto.security;

import com.hkmc.sample.model.enums.Authority;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ReqUser {

    @NotBlank
    @ApiModelProperty(notes = "계정" ,example = "nonohuhu")
    private String account;

    @Size(min = 0, max = 20)
    @ApiModelProperty(notes = "이름" ,example = "임채홍")
    private String name;

    @ApiModelProperty(notes = "비밀번호" ,example = "1234")
    private String password;

    @Email
    @ApiModelProperty(notes = "Email" ,example = "nonohuhu@naver.com")
    private String email;

    @ApiModelProperty(notes = "폰번호" ,example = "01062531828")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "권한" ,example = "ROLE_USER")
    private Authority authority;
}
