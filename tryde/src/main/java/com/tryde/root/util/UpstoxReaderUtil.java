package com.tryde.root.util;

import com.tryde.root.model.Upstoc;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpstoxReaderUtil {

    @Getter
    private List<Upstoc> upstocs = new ArrayList<>(3500);

    public String getInstrumentKey(String tradingSymbol) {
        Upstoc upstoc = upstocs.stream().filter(u -> u.tradingSymbol().equals(tradingSymbol)).findFirst().orElse(null);
        if (upstoc != null) {
            return upstoc.instrumentKey();
        }
        return null;
    }

    public List<String> getInstrumentKeys(String tradingSymbol) {
        List<String> upstoc = upstocs.stream()
                .filter(u -> u.tradingSymbol().equals(tradingSymbol))
                .map(Upstoc::instrumentKey)
                .collect(Collectors.toList());

        return upstoc;
    }

    @EventListener
    private void readUpstoxCsv(ApplicationStartedEvent event) throws IOException {
        List<String> stocks = getStox();
        getAllStocks(stocks);
    }

    private void getAllStocks(List<String> rows) {
        rows.forEach((row) -> {
            String[] vals = row.split(",");
            upstocs.add(toUpstoc(vals));
        });
    }

    private Upstoc toUpstoc(String[] rowArr) {
        return Upstoc.builder()
                .instrumentKey(rowArr[0])
                .exchangeToken(rowArr[1])
                .tradingSymbol(rowArr[2])
                .name(rowArr[3])
                .instrumentType(rowArr[4])
                .exchange(rowArr[5])
                .build();
    }
    private List<String> getStox() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/upstox.csv"));
        List<String> stocks = new ArrayList<>(3500);
        String row = "";

        while ((row = br.readLine())!=null) {
            stocks.add(row);
        }
        return stocks;
    }
}
