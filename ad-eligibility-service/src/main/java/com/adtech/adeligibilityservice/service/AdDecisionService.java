package com.adtech.adeligibilityservice.service;

import com.adtech.adeligibilityservice.ImpressionService;
import com.adtech.adeligibilityservice.dto.ServeAdResponse;
import com.adtech.adeligibilityservice.dto.ServeDecisionResponse;
import com.adtech.adeligibilityservice.model.ServeDecision;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdDecisionService {
    private final PlaybackSessionService sessionService;
    private final CreativeRotationService creativeRotationService;
    private final FrequencyCapService frequencyCapService;
    private final BudgetPacingService budgetPacingService;
    private final ImpressionService impressionService; // make sure this exists

    public ServeAdResponse decide(String userId, Long lineItemId) {

        if (!frequencyCapService.isAllowed(userId, lineItemId))
            return new ServeAdResponse(false, "BLOCKED_FREQUENCY_CAP", null, null, null);

        if (!budgetPacingService.isAllowed(lineItemId))
            return new ServeAdResponse(false, "BLOCKED_PACING", null, null, null);

        // commit
        frequencyCapService.record(userId, lineItemId);
        budgetPacingService.record(lineItemId);
        impressionService.recordImpression(userId, lineItemId);

        String sessionId = sessionService.createSession(userId, lineItemId);

        // choose creative
        var creative = creativeRotationService.nextCreative(lineItemId);

        return new ServeAdResponse(true, null, sessionId, creative.getId(), creative.getName());
    }
}