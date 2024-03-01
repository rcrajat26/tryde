package com.tryde.root.service;

import com.tryde.root.controller.api.UpstoxAdapter;
import com.tryde.root.controller.api.UpstoxApi;
import com.tryde.root.model.BuyData;
import com.tryde.root.model.Candle;
import com.tryde.root.model.api.HistoricalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ta4jexamples.indicators.CandlestickChart;
import ta4jexamples.indicators.IndicatorsToChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoricalDataService {

    private final UpstoxAdapter upstoxApi;
    private final InstrumentService instrumentService;

    public List<Candle> getHistoricDataResult(BuyData buyData) {

        String instrumentKey = getInstrumentKey(buyData);
        String fromDate = getDateString(buyData.date());
        String toDate = getDateString(LocalDate.now());
        HistoricalData data = upstoxApi.getHistoricData(instrumentKey,toDate, fromDate);
        return getCandles(data,toCandle(data.data().candles().get(0),0D).open());
    }

    public Candle getDateResult(BuyData buyData) {

        String instrumentKey = getInstrumentKey(buyData);
        String buyDate = getDateString(buyData.date());
        HistoricalData data = upstoxApi.getDataForDate(instrumentKey, buyDate);
        List<String> dataArr = data != null ? data.data().candles().get(0) : null;
        return toCandle(dataArr, 0D);
    }

    private Candle toCandle(List<String> data, Double buyPrice) {
        if (data == null) {
            return null;
        }
        Double high = Double.parseDouble(data.get(2));
        Double low = Double.parseDouble(data.get(3));
        Double upPercent = 100 * (high - buyPrice) / buyPrice;
        Double dnPercent = 100 * (low - buyPrice) / buyPrice;
        LocalDate date = LocalDate.parse(data.get(0).split("T")[0], DateTimeFormatter.ofPattern(("yyyy-MM-dd")));
        return Candle.builder()
                .date(date)
                .open(Double.parseDouble(data.get(1)))
                .high(high)
                .low(low)
                .close(Double.parseDouble(data.get(4)))
                .volume(Long.parseLong(data.get(5)))
                .openInterest(Long.parseLong(data.get(6)))
                .upPercent(upPercent)
                .dnPercent(dnPercent)
                .build();
    }

    private String getInstrumentKey(BuyData buyData) {
        try {
            return instrumentService.getInstrumentKey(buyData.stock());
        } catch (Exception e) {
            log.warn(e.getMessage());
            return "";
        }
    }
    private String getDateString(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.getYear());
        sb.append("-");
        sb.append(date.getMonth().getValue() < 10 ? "0"+date.getMonth().getValue() : date.getMonth().getValue());
        sb.append("-");
        sb.append(date.getDayOfMonth() < 10 ? "0"+date.getDayOfMonth() : date.getDayOfMonth());
        return sb.toString();
    }

    private List<Candle> getCandles(HistoricalData data, Double buyPrice) {
        if (data == null) {
            return null;
        }
        List<Candle> candles = new ArrayList<>(data.data().candles().size());
        for (List <String> values : data.data().candles()) {
            candles.add(toCandle(values, buyPrice));
        }
        return candles;
    }
}
