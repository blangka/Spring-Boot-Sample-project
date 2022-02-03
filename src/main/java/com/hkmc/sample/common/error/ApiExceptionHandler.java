package com.hkmc.sample.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
* Exception 처리를 Custom 할수 있도록 설정
* */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final ApiException e) {
        log.error("ApiException",e);
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .resultCode(e.getError().getCode())
                        .resultMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final RuntimeException e) {
        log.error("RuntimeException",e);
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .resultCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .resultMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        log.error("AccessDeniedException",e);
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .resultCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .resultMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
        log.error("Exception",e);
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .resultCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .resultMessage(e.getMessage())
                        .build());
    }
}
