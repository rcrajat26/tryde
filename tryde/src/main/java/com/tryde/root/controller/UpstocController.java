package com.tryde.root.controller;

import com.tryde.root.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpstocController {

    private final InstrumentService instrumentService;

    @GetMapping("/stock/{symbol}")
    public String getStock(@PathVariable("symbol") String symbol) {
        return instrumentService.getInstrumentKey(symbol);
    }
}
