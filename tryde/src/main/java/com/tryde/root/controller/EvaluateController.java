package com.tryde.root.controller;

import com.tryde.root.model.TradeVerdict;
import com.tryde.root.service.BuyDataExtractService;
import com.tryde.root.service.StrategyResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EvaluateController {

    private final BuyDataExtractService buyDataExtractService;
    private final StrategyResultService strategyResultService;

    @GetMapping(value = "/evaluate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<TradeVerdict> evaluate(@RequestPart("file")MultipartFile stocks) {
        return strategyResultService.evaluateReturns(buyDataExtractService.getBuyData(stocks));
    }
}
