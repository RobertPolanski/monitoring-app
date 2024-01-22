package com.uniqit.monitoringapp.integration;

import com.uniqit.monitoringapp.TestData;
import com.uniqit.monitoringapp.repository.MonitoringRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.uniqit.monitoringapp.dto.ServerUrlDto.ERROR;
import static com.uniqit.monitoringapp.dto.ServerUrlDto.OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitoringIntegrationTest {
    private static ClientAndServer mockServer1;
    private static ClientAndServer mockServer2;
    private static ClientAndServer mockServer3;

    private static ClientAndServer mockServer4;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoringRepository monitoringRepository;

    @BeforeAll
    public static void startServer() {
        mockServer1 = startClientAndServer(1101);
        mockServer2 = startClientAndServer(1102);
        mockServer3 = startClientAndServer(1103);
        mockServer4 = startClientAndServer(1104);
    }

    @AfterAll
    public static void stopServer() {
        mockServer1.stop();
        mockServer2.stop();
        mockServer3.stop();
        mockServer4.stop();
    }

    @Test
    public void shouldCorrectDiscoverAllServers() throws Exception {
        createStatus200Expectation();
        when(monitoringRepository.getAllUrls()).thenReturn(TestData.getAllLocalUrls());
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[0].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[1].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[2].result", equalTo(OK)));
    }

    @Test
    public void shouldCorrectDiscoverAllServersWithOneStatus404() throws Exception {
        createStatus200Expectation();
        createStatus404Expectation();
        when(monitoringRepository.getAllUrls()).thenReturn(TestData.getAllLocalUrlsWithOneForHttpStatus404());
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[0].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[1].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[2].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[3].result", equalTo(ERROR + " Status: 404")));
    }

    @Test
    public void shouldCorrectDiscoverAllServersWithOneStatus404AndOneUnreachable() throws Exception {
        createStatus200Expectation();
        createStatus404Expectation();
        when(monitoringRepository.getAllUrls()).thenReturn(TestData.getAllLocalUrlsWithOneForHttpStatus404AndOneUnreachable());
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[0].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[1].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[2].result", equalTo(OK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[3].result", equalTo(ERROR + " Status: 404")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[4].result", equalTo(ERROR + " Url address unreachable")));
    }

    private void createStatus200Expectation() {
        for (int i = 1101; i <= 1103; i++) {
            new MockServerClient("localhost", i)
                    .when(
                            request()
                                    .withMethod("HEAD")
                                    .withPath("/healthy"),
                            exactly(1))
                    .respond(
                            response()
                                    .withStatusCode(200)
                    );
        }
    }

    private void createStatus404Expectation() {
        new MockServerClient("localhost", 1104)
                .when(
                        request()
                                .withMethod("HEAD")
                                .withPath("/healthy"),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(404)
                );

    }
}
