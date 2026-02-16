package com.adtech.adeligibilityservice.dto;


import com.adtech.adeligibilityservice.model.VideoEventType;
import lombok.Data;

@Data
public class VideoEventRequest {
    private String userId;
    private Long lineItemId;
    private VideoEventType event;
    private String sessionId;

}
