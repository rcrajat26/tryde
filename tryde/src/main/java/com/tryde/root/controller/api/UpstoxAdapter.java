package com.tryde.root.controller.api;

import com.tryde.root.model.api.HistoricalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpstoxAdapter {
    private final UpstoxApi upstoxApi;

    public HistoricalData getHistoricData(String instrumentKey, String toDate, String fromDate) {
        try {
            return upstoxApi.getHistoricData(instrumentKey, toDate, fromDate);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public HistoricalData getDataForDate(String instrumentKey, String date) {
        try {
            return upstoxApi.getHistoricData(instrumentKey, date, date);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }
}
