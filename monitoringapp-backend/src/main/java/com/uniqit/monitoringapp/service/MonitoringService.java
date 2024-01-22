package com.uniqit.monitoringapp.service;

import com.uniqit.monitoringapp.dto.ServerUrlDto;
import com.uniqit.monitoringapp.repository.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MonitoringRepository serverUrlRepository;

    private final PingUrlHelper pingUrlHelper;

    public List<ServerUrlDto> getAllServersUrlInfos() {
        List<String> allUrls = serverUrlRepository.getAllUrls();
        List<ServerUrlDto> serverUrlDtoList = pingUrlHelper.pingURLs(allUrls);
        serverUrlDtoList.sort(Comparator.comparing(ServerUrlDto::getUrl));
        return serverUrlDtoList;
    }


}
