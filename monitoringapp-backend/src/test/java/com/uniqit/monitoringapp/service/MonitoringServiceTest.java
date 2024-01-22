package com.uniqit.monitoringapp.service;

import com.uniqit.monitoringapp.dto.ServerUrlDto;
import com.uniqit.monitoringapp.repository.MonitoringRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.uniqit.monitoringapp.TestData.getAllUrls;
import static com.uniqit.monitoringapp.TestData.getServerUrlResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitoringServiceTest {

    @Mock
    private MonitoringRepository monitoringRepository;
    @Mock
    private PingUrlHelper pingUrlHelper;

    private MonitoringService monitoringService;

    @Test
    public void shouldReturnCorrectData() {
        when(monitoringRepository.getAllUrls()).thenReturn(getAllUrls());
        when(pingUrlHelper.pingURLs(getAllUrls())).thenReturn(getServerUrlResult());

        monitoringService = new MonitoringService(monitoringRepository, pingUrlHelper);

        List<ServerUrlDto> allServersUrlInfos = monitoringService.getAllServersUrlInfos();
        assertThat(allServersUrlInfos).hasSize(8);
        assertThat(allServersUrlInfos.get(0).getUrl()).isEqualTo("http://www.example.com");
        assertThat(allServersUrlInfos.get(1).getUrl()).isEqualTo("http://www.google.com");
        assertThat(allServersUrlInfos.get(2).getUrl()).isEqualTo("http://www.google.de");
        assertThat(allServersUrlInfos.get(3).getUrl()).isEqualTo("http://www.interia.pl");
        assertThat(allServersUrlInfos.get(7).getUrl()).isEqualTo("https://stackoverflow.com");
    }

}
