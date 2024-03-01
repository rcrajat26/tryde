package com.tryde.root.controller.api;

import com.tryde.root.model.api.HistoricalData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UpstoxApi", url = "https://api.upstox.com/v2/")
public interface UpstoxApi {

    @GetMapping("/historical-candle/{instrumentKey}/day/{toDate}/{fromDate}")
    HistoricalData getHistoricData(@PathVariable String instrumentKey,
                                   @PathVariable String toDate,
                                   @PathVariable String fromDate);
}
