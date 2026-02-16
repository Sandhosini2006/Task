package com.adtech.adeligibilityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PlaybackSessionService {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "session:";

    public String createSession(String userId, Long lineItemId) {

        String sessionId = UUID.randomUUID().toString();

        String key = PREFIX + sessionId;

        // store session → valid for 10 minutes
        redisTemplate.opsForHash().put(key, "userId", userId);
        redisTemplate.opsForHash().put(key, "lineItemId", lineItemId.toString());

        redisTemplate.expire(key, 10, TimeUnit.MINUTES);

        return sessionId;
    }

    public boolean isValid(String sessionId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + sessionId));
    }

    public boolean markEvent(String sessionId, String event) {
        String key = PREFIX + sessionId + ":events";

        // prevent duplicate events
        Boolean first = redisTemplate.opsForSet().add(key, event) == 1;
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);

        return first;
    }
}
