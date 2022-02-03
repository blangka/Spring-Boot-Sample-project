package com.hkmc.sample.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "목록 요청")
public class ReqList {

  @ApiModelProperty(value = "목록 페이지", required = true, example = "1")
  private int page = 1;

  @ApiModelProperty(value = "목록 크기", required = true, example = "10")
  private int size = 10;
}
