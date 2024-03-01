package com.tryde.root.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TradeVerdict {
    private BuyData stock;
    private Double boughtPrice;
    private String verdict;
    private Candle highestGain;
    private Candle highestLoss;
    private Candle dateGainLevel1;
    private Candle dateGainLevel2;
    private Candle dateGainLevel3;
    private Candle dateDownLevel1;
    private Candle dateDownLevel2;
    private Candle dateDownLevel3;
}
