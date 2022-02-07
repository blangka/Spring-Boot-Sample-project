package com.hkmc.sample.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "응답(JSON)")
public class ResJson<T> {

  @ApiModelProperty(notes = "Result code", required = true)
  private String resultCode;

  @ApiModelProperty(notes = "Result message")
  private String resultMessage;

  @ApiModelProperty(notes = "Result value")
  private T result;

  public ResJson(String resultCode, String resultMessage) {
    super();
    this.resultCode = resultCode;
    this.resultMessage = resultMessage;
  }

  public ResJson(T result) {
    super();
    this.resultCode = "0000";
    this.resultMessage = "SUCCESS";
    this.result = result;
  }
}