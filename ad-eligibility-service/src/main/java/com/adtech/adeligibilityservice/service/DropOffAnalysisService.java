package com.adtech.adeligibilityservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DropOffAnalysisService {

    private final StringRedisTemplate redisTemplate;

    public long getCount(String key) {
        String val = redisTemplate.opsForValue().get(key);
        return val == null ? 0 : Long.parseLong(val);
    }

    public long[] loadCounts(Long lineItemId) {

        String today = LocalDate.now().toString();

        long start = getCount("video:" + lineItemId + ":START:" + today);
        long q1 = getCount("video:" + lineItemId + ":FIRST_QUARTILE:" + today);
        long mid = getCount("video:" + lineItemId + ":MIDPOINT:" + today);
        long q3 = getCount("video:" + lineItemId + ":THIRD_QUARTILE:" + today);
        long complete = getCount("video:" + lineItemId + ":COMPLETE:" + today);

        return new long[]{start, q1, mid, q3, complete};
    }

    public String biggestDrop(long[] c) {

        String[] names = {"START→25%", "25%→50%", "50%→75%", "75%→COMPLETE"};

        long maxDrop = -1;
        String worst = "NONE";

        for (int i = 0; i < c.length - 1; i++) {
            long drop = c[i] - c[i + 1];
            if (drop > maxDrop) {
                maxDrop = drop;
                worst = names[i];
            }
        }

        return worst;
    }
}
