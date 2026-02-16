package com.adtech.adeligibilityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FrequencyCapService {

    private final StringRedisTemplate redisTemplate;

    private static final int DAILY_CAP = 5;

    // CHECK PHASE (read only)
    public boolean isAllowed(String userId, Long lineItemId) {

        String today = LocalDate.now().toString();
        String key = String.format("fcap:%s:%d:%s", userId, lineItemId, today);

        String value = redisTemplate.opsForValue().get(key);
        long count = value == null ? 0 : Long.parseLong(value);

        return count < DAILY_CAP;
    }

    // COMMIT PHASE (write only after ad served)
    public void record(String userId, Long lineItemId) {

        String today = LocalDate.now().toString();
        String key = String.format("fcap:%s:%d:%s", userId, lineItemId, today);

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
        }
    }
}
