package com.tryde.root.service;

import com.tryde.root.util.UpstoxReaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstrumentService {
    private final UpstoxReaderUtil upstoxUtil;

    public String getInstrumentKey(String stock) {
        List<String> instrumentKeys = upstoxUtil.getInstrumentKeys(stock);
        if (instrumentKeys.size() == 0) {
            throw new RuntimeException("No instrument keys found");
        }
        if (instrumentKeys.size() > 1) {
            log.info("================================================================================================");
            log.info("Stock= {} -> multiple values={}", stock, instrumentKeys);
            log.info("================================================================================================");
            throw new RuntimeException("Multiple instrument keys found");
        }
        return instrumentKeys.get(0);
    }
}
