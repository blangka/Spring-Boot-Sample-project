package com.hkmc.sample.model.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 생성자 주입
public enum OrderStatus {

    ORDER("주문"),
    CANCLE("취소"),
    ;

    @NonNull
    private final String value;
}
