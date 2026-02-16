package com.adtech.adeligibilityservice.service;


import com.adtech.adeligibilityservice.model.VideoEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VideoEventService {

    private final StringRedisTemplate redisTemplate;

//    public void recordEvent(String userId, Long lineItemId, VideoEventType event) {
//
//        String today = LocalDate.now().toString();
//
//        String campaignKey = "video:" + lineItemId + ":" + event + ":" + today;
//        String userKey = "video:user:" + userId + ":" + event + ":" + today;
//
//        redisTemplate.opsForValue().increment(campaignKey);
//        redisTemplate.opsForValue().increment(userKey);
//
//        redisTemplate.expire(campaignKey, 1, TimeUnit.DAYS);
//        redisTemplate.expire(userKey, 1, TimeUnit.DAYS);
//    }
    private final PlaybackSessionService sessionService;

    public boolean recordEvent(String sessionId, Long lineItemId, VideoEventType event) {

        if (!sessionService.isValid(sessionId))
            return false;

        // ignore duplicate event for same playback
        if (!sessionService.markEvent(sessionId, event.name()))
            return false;

        String today = LocalDate.now().toString();

        String campaignKey = "video:" + lineItemId + ":" + event + ":" + today;

        redisTemplate.opsForValue().increment(campaignKey);
        redisTemplate.expire(campaignKey, 1, TimeUnit.DAYS);

        return true;
    }

}

