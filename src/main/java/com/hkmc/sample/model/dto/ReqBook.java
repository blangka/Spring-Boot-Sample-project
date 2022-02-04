package com.hkmc.sample.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "상품 정보")
public class ReqBook {

    private Long id;

    @ApiModelProperty(notes = "이름")
    private String name;
    @ApiModelProperty(notes = "가격")
    private int price;
    @ApiModelProperty(notes = "재고")
    private int stockQuantity;

    private String author;
    private String isbn;
}
