package com.tryde.root.service;

import com.tryde.root.model.Candle;
import com.tryde.root.model.TradeVerdict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeVerdictService {

    public TradeVerdict getVerdict(List<Candle> candles) {
        TradeVerdict verdict = new TradeVerdict();
        for (Candle candle : candles) {
            if (candle.upPercent() >= 7.5) {
                log.info("Trade success sold @{}", candle.high());
                verdict.setVerdict("SUCCESS");
                verdict.setDateGainLevel3(candle);
                break;
            } else if (candle.dnPercent() <= -8) {
                log.info("Trade failed sold @{}", candle.low());
                verdict.setVerdict("FAILURE");
                verdict.setDateDownLevel3(candle);
                break;
            } if (candle.upPercent() < 7.5 && candle.upPercent() >= 5) {
                log.info("Gain Level 2 reached sold portion");
                if (verdict.getDateGainLevel2() == null) {
                    verdict.setDateGainLevel2(candle);
                }
            } if (candle.upPercent() < 5 && candle.upPercent() >= 3.5) {
                if (verdict.getDateGainLevel1() == null) {
                    log.info("Gain Level 1 reached sold portion");
                    verdict.setDateGainLevel1(candle);
                }
            } if (candle.dnPercent() > -5 && candle.dnPercent() <= -2.5) {
                if (verdict.getDateDownLevel1() == null) {
                    log.info("Down Level 1 reached bought portion");
                    verdict.setDateDownLevel1(candle);
                }
            } if (candle.dnPercent() > -8 && candle.dnPercent() <= -   5) {
                if (verdict.getDateDownLevel2() == null) {
                    log.info("Down Level 2 reached bought portion");
                    verdict.setDateDownLevel2(candle);
                }
            }
        }
        verdict.setHighestGain(candles.stream().max(Comparator.comparing(Candle::upPercent)).orElse(null));
        verdict.setHighestLoss(candles.stream().max(Comparator.comparing(Candle::dnPercent)).orElse(null));
        return verdict;
    }
}
