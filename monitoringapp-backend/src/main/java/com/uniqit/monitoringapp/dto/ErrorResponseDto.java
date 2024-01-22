package com.uniqit.monitoringapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String errorCode;
    private String description;
}
