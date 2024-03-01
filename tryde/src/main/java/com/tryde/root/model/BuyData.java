package com.tryde.root.model;

import lombok.Builder;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record BuyData(
        LocalDate date,
        String stock
) {
}
