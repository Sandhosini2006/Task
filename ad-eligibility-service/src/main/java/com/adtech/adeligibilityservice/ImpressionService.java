package com.adtech.adeligibilityservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ImpressionService {

    private final StringRedisTemplate redisTemplate;

    public void recordImpression(String userId, Long lineItemId) {

        String today = LocalDate.now().toString();

        String campaignKey = "stats:lineItem:" + lineItemId + ":" + today;
        String userKey = "stats:user:" + userId + ":" + today;

        redisTemplate.opsForValue().increment(campaignKey);
        redisTemplate.opsForValue().increment(userKey);

        // expire daily automatically
        redisTemplate.expire(campaignKey, 1, TimeUnit.DAYS);
        redisTemplate.expire(userKey, 1, TimeUnit.DAYS);
    }
}
