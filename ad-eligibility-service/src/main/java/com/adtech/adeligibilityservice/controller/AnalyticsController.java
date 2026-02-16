package com.adtech.adeligibilityservice.controller;


import com.adtech.adeligibilityservice.dto.DropOffResponse;
import com.adtech.adeligibilityservice.service.DropOffAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final DropOffAnalysisService service;

    @GetMapping("/dropoff/{lineItemId}")
    public DropOffResponse dropOff(@PathVariable Long lineItemId) {

        long[] c = service.loadCounts(lineItemId);

        String worst = service.biggestDrop(c);

        return new DropOffResponse(
                c[0], c[1], c[2], c[3], c[4],
                worst
        );
    }
}
