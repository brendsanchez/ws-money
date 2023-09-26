package com.dusk.money.dto.response;

import com.dusk.money.dto.PriceVal;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Price(
        int index,
        String name,
        String lastUpdated,
        PriceVal priceBuy,
        PriceVal priceSell,
        String variation,
        String volume,
        Date timestamp,
        String spread) {
    public Price(int index, String name, String lastUpdated, PriceVal priceBuy, PriceVal priceSell) {
        this(index, name, lastUpdated, priceBuy, priceSell, null, null, null, null);
    }
}
