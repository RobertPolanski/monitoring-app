package com.uniqit.monitoringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerUrlDto {

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";


    private String url;
    private String result;
}
