package com.hkmc.sample.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.persistence.Embeddable;

/*
@Setter 를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만들자.
JPA 스펙상 엔티티나 임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는 protected 로 설정해야 한다.
public 으로 두는 것 보다는 protected 로 설정하는 것이 그나마 더 안전 하다.
 */
@Embeddable
@Getter
public class Address {

    @ApiModelProperty(notes = "도시", example = "youngIn")
    private String city;
    @ApiModelProperty(notes = "거리명", example = "suji")
    private String street;
    @ApiModelProperty(notes = "zipCode", example = "668-2")
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
