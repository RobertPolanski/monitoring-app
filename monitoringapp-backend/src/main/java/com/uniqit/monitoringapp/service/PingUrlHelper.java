package com.uniqit.monitoringapp.service;

import com.uniqit.monitoringapp.config.PingUrlConfig;
import com.uniqit.monitoringapp.dto.ServerUrlDto;
import com.uniqit.monitoringapp.exception.ServerUrlPingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public class PingUrlHelper {

    private final PingUrlConfig pingUrlConfig;

    public List<ServerUrlDto> pingURLs(List<String> urls) {

        List<ServerUrlDto> resultList = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(pingUrlConfig.getPoolSize());

        List<Callable<ServerUrlDto>> tasks = new ArrayList<>();

        urls.forEach(u -> tasks.add(new PingUrlCallable(u, pingUrlConfig.getConnectionTimeout(), pingUrlConfig.getReadTimeout())));

        CompletionService<ServerUrlDto> completionService = new ExecutorCompletionService<>(pool);
        tasks.forEach(completionService::submit);

        try {
            for (int i = 0; i < tasks.size(); i++) {
                Future<ServerUrlDto> future = completionService.take();
                resultList.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new ServerUrlPingFailedException(e);
        } finally {
            pool.shutdown();
        }
        return resultList;
    }
}
