package com.uniqit.monitoringapp.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MonitoringRepository {
    public List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("http://www.example.com");
        urls.add("http://www.google.com");
        urls.add("http://www.google.de");
        urls.add("http://www.onet.pl");
        urls.add("http://www.wp.pl");
        urls.add("http://www.unreachable-server.com");
        urls.add("http://www.interia.pl");
        urls.add("http://localhost:8081");
        urls.add("http://localhost:8082");

        return urls;
    }
}
