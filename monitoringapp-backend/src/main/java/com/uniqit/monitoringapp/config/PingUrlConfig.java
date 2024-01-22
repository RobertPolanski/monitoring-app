package com.uniqit.monitoringapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "monitoring-app.ping-url")
@Data
public class PingUrlConfig {
    private int connectionTimeout;

    private int readTimeout;

    private int poolSize;
}
