package com.tryde.root.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Candle(
        LocalDate date,
        Double open,
        Double high,
        Double low,
        Double close,
        Long volume,
        Long openInterest,
        Double upPercent,
        Double dnPercent
 ) {
}
