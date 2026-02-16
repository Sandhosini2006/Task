package com.adtech.adeligibilityservice.service;

import com.adtech.adeligibilityservice.model.Creative;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreativeRotationService {

    private final StringRedisTemplate redisTemplate;

    // pretend advertiser uploaded 3 videos
    private final List<Creative> creatives = List.of(
            new Creative(1L, "Car Brand Video"),
            new Creative(2L, "Feature Video"),
            new Creative(3L, "Offer Video")
    );

    public Creative nextCreative(Long lineItemId) {

        String key = "rotate:" + lineItemId;

        Long counter = redisTemplate.opsForValue().increment(key);

        int index = (int) ((counter - 1) % creatives.size());

        return creatives.get(index);
    }
}
