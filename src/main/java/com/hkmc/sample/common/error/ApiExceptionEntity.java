package com.hkmc.sample.common.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {
    private String resultCode;
    private String resultMessage;

    @Builder
    public ApiExceptionEntity(HttpStatus status, String resultCode, String resultMessage){
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
