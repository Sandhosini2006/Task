package com.adtech.adeligibilityservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServeAdResponse {
    private boolean served;
    private String reason;
    private String sessionId;
    private Long creativeId;
    private String creativeName;
}
