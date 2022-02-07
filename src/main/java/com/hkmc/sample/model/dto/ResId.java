package com.hkmc.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "save 성공시 Id 반환")
public class ResId {

    @ApiModelProperty(notes = "")
    private Long savedId;
}
