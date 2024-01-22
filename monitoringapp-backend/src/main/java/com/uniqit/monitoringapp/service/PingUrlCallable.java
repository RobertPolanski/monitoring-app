package com.uniqit.monitoringapp.service;

import com.uniqit.monitoringapp.dto.ServerUrlDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import static com.uniqit.monitoringapp.dto.ServerUrlDto.ERROR;
import static com.uniqit.monitoringapp.dto.ServerUrlDto.OK;

@RequiredArgsConstructor
public class PingUrlCallable implements Callable<ServerUrlDto> {
    private final String url;
    private final int connectionTimeout;
    private final int readTimeout;


    @Override
    public ServerUrlDto call() {
        String replacedUrl = url.replaceFirst("^https", "http");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(replacedUrl).openConnection();
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            String result = (200 <= responseCode && responseCode <= 399) ? OK : ERROR + " Status: " + responseCode;
            return new ServerUrlDto(replacedUrl, result);
        } catch (IOException exception) {
            return new ServerUrlDto(replacedUrl, ERROR + " Url address unreachable");
        }
    }
}
