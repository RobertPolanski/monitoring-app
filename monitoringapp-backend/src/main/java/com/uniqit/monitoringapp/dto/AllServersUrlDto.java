package com.uniqit.monitoringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllServersUrlDto {
    private List<ServerUrlDto> serverUrlDtoList;
}
