package com.hkmc.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 정보")
public class ReqMember {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    @ApiModelProperty(notes = "이름")
    private String name;

    @ApiModelProperty(notes = "시")
    private String city;

    @ApiModelProperty(notes = "도")
    private String street;

    @ApiModelProperty(notes = "주소")
    private String zipcode;
}
