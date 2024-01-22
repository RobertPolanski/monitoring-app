package com.uniqit.monitoringapp;

import com.uniqit.monitoringapp.dto.ServerUrlDto;

import java.util.ArrayList;
import java.util.List;

import static com.uniqit.monitoringapp.dto.ServerUrlDto.ERROR;
import static com.uniqit.monitoringapp.dto.ServerUrlDto.OK;

public final class TestData {

    private TestData() {}

    public static List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("http://www.google.com");
        urls.add("http://www.google.de");
        urls.add("http://www.onet.pl");
        urls.add("http://www.wp.pl");
        urls.add("http://www.example.com");
        urls.add("http://www.interia.pl");
        urls.add("http://www.interia.pl/fake");
        urls.add("https://stackoverflow.com");
        return urls;
    }

    public static List<String> getAllLocalUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("http://localhost:1101/healthy");
        urls.add("http://localhost:1102/healthy");
        urls.add("http://localhost:1103/healthy");
        return urls;
    }

    public static List<String> getAllLocalUrlsWithOneForHttpStatus404() {
        List<String> urls = new ArrayList<>();
        urls.addAll(getAllLocalUrls());
        urls.add("http://localhost:1104/healthy");
        return urls;
    }

    public static List<String> getAllLocalUrlsWithOneForHttpStatus404AndOneUnreachable() {
        List<String> urls = new ArrayList<>();
        urls.addAll(getAllLocalUrlsWithOneForHttpStatus404());
        urls.add("http://localhost:1105/healthy");
        return urls;
    }
    public static List<ServerUrlDto> getServerUrlResult() {
        List<ServerUrlDto> serverUrlDtoList = new ArrayList<>();
        serverUrlDtoList.add(new ServerUrlDto("http://www.google.com", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.google.de", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.onet.pl", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.wp.pl", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.example.com", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.interia.pl", OK));
        serverUrlDtoList.add(new ServerUrlDto("http://www.interia.pl/fake", ERROR));
        serverUrlDtoList.add(new ServerUrlDto("https://stackoverflow.com", OK));
        return serverUrlDtoList;
    }
}
