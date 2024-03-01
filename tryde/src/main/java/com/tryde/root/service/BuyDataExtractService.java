package com.tryde.root.service;

import com.tryde.root.controller.api.UpstoxApi;
import com.tryde.root.model.BuyData;
import com.tryde.root.model.Candle;
import com.tryde.root.model.api.HistoricalData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyDataExtractService {

    private final UpstoxApi upstoxApi;
    private final InstrumentService instrumentService;

    public List<BuyData> getBuyData(MultipartFile file) {
        return getBuyData(getRows(file));
    }



    private String[] getRows(MultipartFile file) {
        String rows[] = null;
        try {
            byte[] bytes = file.getBytes();
            String data = new String(bytes);
            rows = data.split("\r\n");
        } catch (Exception e) {
            System.out.println();
        }
        return rows;
    }

    private List<BuyData> getBuyData(String[] rows) {
        List<BuyData> buyDataList = new ArrayList<>(rows.length);
        for (String row : rows) {
            String[] values = row.split(",");
            try {
                LocalDate date = LocalDate.parse(values[0],DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                buyDataList.add(BuyData.builder().date(date).stock(values[1]).build());
            } catch (Exception e) {
                System.out.println();
            }

        }
        return buyDataList;
    }
}
