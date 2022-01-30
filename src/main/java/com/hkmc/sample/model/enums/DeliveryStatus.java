package com.hkmc.sample.model.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("준비"),
    COMP("배송"),
    ;

    @NonNull
    private final String value;
}
