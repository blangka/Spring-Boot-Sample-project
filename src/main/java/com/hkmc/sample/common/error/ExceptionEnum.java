package com.hkmc.sample.common.error;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),

    DUPLICATE_MEMBER(HttpStatus.NOT_ACCEPTABLE, "S0001", "기 가입된 회원입니다."),
    NOT_ENOUGH_STOCK(HttpStatus.NOT_ACCEPTABLE, "S0002", "재고 수량이 모자랍니다."),
    STATUS_CHANGED_ERROR(HttpStatus.NOT_ACCEPTABLE, "S0003", "상태 변경이 되지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}