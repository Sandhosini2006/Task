package com.adtech.adeligibilityservice.dto;


import lombok.Data;

@Data
public class ServeAdRequest {
    private String userId;
    private Long lineItemId;
}
