package com.tryde.root.service;

import com.tryde.root.model.BuyData;
import com.tryde.root.model.Candle;
import com.tryde.root.model.TradeVerdict;
import com.tryde.root.model.api.HistoricalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StrategyResultService {
    private final HistoricalDataService historicalDataService;
    private final TradeVerdictService tradeVerdictService;

    public List<TradeVerdict> evaluateReturns(List<BuyData> buyDataList) {
        List<TradeVerdict> verdicts = new ArrayList<>(buyDataList.size());
        for (int i = 0; i < buyDataList.size(); i++) {
            BuyData buyData = buyDataList.get(i);
            Candle buyCandle = historicalDataService.getDateResult(buyData);
            if (buyCandle == null) {
                TradeVerdict verdict = new TradeVerdict();
                verdict.setVerdict("STOCK_NOT_FOUND");
                verdicts.add(verdict);
                continue;
            }
            Double buyPrice = buyCandle.open();
            List<Candle> candles = historicalDataService.getHistoricDataResult(buyData);
            TradeVerdict tradeVerdict = tradeVerdictService.getVerdict(candles);
            if (tradeVerdict.getVerdict() == null) {
                tradeVerdict.setVerdict("TRADE_RUNNING");
            }
            tradeVerdict.setStock(buyData);
            tradeVerdict.setBoughtPrice(buyPrice);
            verdicts.add(tradeVerdict);
            log.info("{}. Stock={}, date={} at {}", i + 1, buyData.stock(), buyData.date(), buyPrice);
        }
        Long successCnt = verdicts.stream().filter(v -> v.getVerdict().equals("SUCCESS")).count();
        Long failCount = verdicts.stream().filter(v -> v.getVerdict().equals("FAILURE")).count();
        Long notFndCnt = verdicts.stream().filter(v -> v.getVerdict().equals("STOCK_NOT_FOUND")).count();
        Long trdRunningCnt = verdicts.stream().filter(v -> v.getVerdict().equals("TRADE_RUNNING")).count();
        log.info("Analysis done. Total={} - Successful={}, Failed={}, Not Found={}, Trades running={}",
                verdicts.size(), successCnt, failCount, notFndCnt, trdRunningCnt);
        return verdicts;
    }
}
