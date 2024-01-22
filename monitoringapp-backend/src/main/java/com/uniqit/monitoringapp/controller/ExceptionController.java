package com.uniqit.monitoringapp.controller;

import com.uniqit.monitoringapp.dto.ErrorResponseDto;
import com.uniqit.monitoringapp.exception.BaseWebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<ErrorResponseDto> defaultExceptionHandler(Exception exception) {
        log.error("Unexpected error occurred", exception);
        return ResponseEntity.internalServerError().body(new ErrorResponseDto("ERR_500", "Please check logs for more information"));
    }

    @ExceptionHandler({ BaseWebException.class })
    public ResponseEntity<ErrorResponseDto> handleException(BaseWebException baseWebException) {
        return baseWebException.getResponseEntity();
    }
}
