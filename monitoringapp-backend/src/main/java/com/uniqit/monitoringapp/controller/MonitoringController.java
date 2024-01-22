package com.uniqit.monitoringapp.controller;

import com.uniqit.monitoringapp.dto.AllServersUrlDto;
import com.uniqit.monitoringapp.dto.ErrorResponseDto;
import com.uniqit.monitoringapp.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Operation(tags = { "Server Url Monitoring" }, summary = "Monitor Url on Server.", description = """
			The service monitors urls on servers.
			Returns a list of urls and a status whether urls are reachable.
			""", operationId = "monitorServerUrls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The servers were monitored. The list of the servers is returned. ",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AllServersUrlDto.class)) }),
            @ApiResponse(responseCode = "400", description = """
					Your request will not be processed, because there was an error in it.
					""",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)) }) })
    @GetMapping("/all")
    public AllServersUrlDto getAllServers() {
        return new AllServersUrlDto(monitoringService.getAllServersUrlInfos());
    }
}
