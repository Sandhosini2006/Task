package com.adtech.adeligibilityservice.controller;

import com.adtech.adeligibilityservice.dto.VideoEventRequest;
import com.adtech.adeligibilityservice.service.VideoEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class VideoEventController {

    private final VideoEventService videoEventService;

    @PostMapping("/video-event")
    public String track(@RequestBody VideoEventRequest request) {

        videoEventService.recordEvent(
                request.getUserId(),
                request.getLineItemId(),
                request.getEvent()
        );

        return "EVENT_RECORDED";
    }
}
