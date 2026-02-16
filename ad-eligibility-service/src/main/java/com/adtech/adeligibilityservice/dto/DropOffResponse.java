package com.adtech.adeligibilityservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DropOffResponse {

    private long start;
    private long firstQuartile;
    private long midpoint;
    private long thirdQuartile;
    private long complete;

    private String biggestDropStage;
}
