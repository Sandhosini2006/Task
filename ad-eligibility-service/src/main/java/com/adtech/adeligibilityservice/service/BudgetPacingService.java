package com.adtech.adeligibilityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BudgetPacingService {

    private final StringRedisTemplate redisTemplate;

    private static final int HOURLY_LIMIT = 10;

    // CHECK PHASE (read only)
    public boolean isAllowed(Long lineItemId) {

        String hour = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));

        String key = "pacing:" + lineItemId + ":" + hour;

        String value = redisTemplate.opsForValue().get(key);
        long count = value == null ? 0 : Long.parseLong(value);

        return count < HOURLY_LIMIT;
    }

    // COMMIT PHASE (write only after ad served)
    public void record(Long lineItemId) {

        String hour = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));

        String key = "pacing:" + lineItemId + ":" + hour;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, 1, TimeUnit.HOURS);
        }
    }
}
