package com.uniqit.monitoringapp.controller;

import com.uniqit.monitoringapp.exception.ServerUrlPingFailedException;
import com.uniqit.monitoringapp.service.MonitoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.uniqit.monitoringapp.TestData.getServerUrlResult;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoringController.class)
public class MonitoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoringService monitoringService;

    @Test
    public void shouldReturnCorrectMessage() throws Exception {
        when(monitoringService.getAllServersUrlInfos()).thenReturn(getServerUrlResult());
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serverUrlDtoList[*]", hasSize(8)));


    }

    @Test
    public void shouldReturnCorrectErrorMessageByRuntimeException() throws Exception {
        when(monitoringService.getAllServersUrlInfos()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalTo("ERR_500")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", equalTo("Please check logs for more information")));



    }

    @Test
    public void shouldReturnCorrectErrorMessageByBaseWebException() throws Exception {
        when(monitoringService.getAllServersUrlInfos()).thenThrow(new ServerUrlPingFailedException(new RuntimeException()));
        mockMvc.perform(get("/monitoring/all"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalTo("ERR_500_SERVER_URL_PING_FAILED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", equalTo("Server url ping failed")));



    }
}
