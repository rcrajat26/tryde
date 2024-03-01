package com.tryde.root.model;

import lombok.Builder;
import lombok.Setter;

@Builder
public record Upstoc(
        String instrumentKey,
        String exchangeToken,
        String tradingSymbol,
        String name,
        String instrumentType,
        String exchange
) {
}
