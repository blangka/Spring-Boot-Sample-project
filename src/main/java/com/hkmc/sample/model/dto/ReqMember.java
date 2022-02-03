package com.hkmc.sample.model.dto;

import com.hkmc.sample.entity.Address;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "회원 정보")
public class ReqMember {

    @ApiModelProperty(notes = "이름", example = "lim")
    private String name;

    @ApiModelProperty("주소")
    private Address address;
}
