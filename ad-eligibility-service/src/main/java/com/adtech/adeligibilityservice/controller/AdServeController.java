package com.adtech.adeligibilityservice.controller;

import com.adtech.adeligibilityservice.dto.ServeAdRequest;
import com.adtech.adeligibilityservice.dto.ServeAdResponse;
import com.adtech.adeligibilityservice.dto.ServeDecisionResponse;
import com.adtech.adeligibilityservice.model.ServeDecision;
import com.adtech.adeligibilityservice.service.AdDecisionService;
import com.adtech.adeligibilityservice.service.FrequencyCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdServeController {

    private final AdDecisionService adDecisionService;

    @PostMapping("/serve")
    public ServeAdResponse serveAd(@RequestBody ServeAdRequest request) {
        return adDecisionService.decide(
                request.getUserId(),
                request.getLineItemId()
        );
    }
}

