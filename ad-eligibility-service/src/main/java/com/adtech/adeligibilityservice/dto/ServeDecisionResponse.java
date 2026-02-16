package com.adtech.adeligibilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServeDecisionResponse {

    private boolean served;
    private String reason;
    private String sessionId;

    public static ServeDecisionResponse serve(String sessionId) {
        return new ServeDecisionResponse(true, null, sessionId);
    }

    public static ServeDecisionResponse block(String reason) {
        return new ServeDecisionResponse(false, reason, null);
    }
}
