package com.uniqit.monitoringapp.exception;

import com.uniqit.monitoringapp.dto.ErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class BaseWebException extends RuntimeException {
    private final HttpStatus status;

    private final String errorCode;

    private final String description;

    public BaseWebException(HttpStatus status, String errorCode, String description) {
        this.status = status;
        this.errorCode = errorCode;
        this.description = description;
    }

    public BaseWebException(Throwable cause, HttpStatus status, String errorCode, String description) {
        super(cause);
        this.status = status;
        this.errorCode = errorCode;
        this.description = description;
    }

    public ResponseEntity<ErrorResponseDto> getResponseEntity() {
        return ResponseEntity.status(this.status).body(getResponseDto());
    }

    public ErrorResponseDto getResponseDto() {
        return new ErrorResponseDto(this.errorCode, this.description);
    }
}
