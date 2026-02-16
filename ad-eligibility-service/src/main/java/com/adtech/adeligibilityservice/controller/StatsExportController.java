package com.adtech.adeligibilityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class StatsExportController {

    private final StringRedisTemplate redisTemplate;

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=video-report.csv");

        PrintWriter writer = response.getWriter();

        // new header
        writer.println("lineItem,date,impressions,starts,completes,completion_rate");

        String today = LocalDate.now().toString();

        // find all campaigns that had impressions
        Set<String> impressionKeys = redisTemplate.keys("stats:lineItem:*:" + today);

        if (impressionKeys != null) {
            for (String key : impressionKeys) {

                // stats:lineItem:10:2026-02-16
                String[] parts = key.split(":");
                String lineItemId = parts[2];

                // impressions
                long impressions = getLong(key);

                // video start
                String startKey = "video:" + lineItemId + ":START:" + today;
                long starts = getLong(startKey);

                // video complete
                String completeKey = "video:" + lineItemId + ":COMPLETE:" + today;
                long completes = getLong(completeKey);

                // calculate completion rate
                double completionRate = starts == 0 ? 0 : ((double) completes / starts) * 100;

                writer.println(
                        lineItemId + "," +
                                today + "," +
                                impressions + "," +
                                starts + "," +
                                completes + "," +
                                String.format("%.2f", completionRate)
                );
            }
        }

        writer.flush();
        writer.close();
    }

    private long getLong(String key) {
        String val = redisTemplate.opsForValue().get(key);
        return val == null ? 0 : Long.parseLong(val);
    }
}
