package com.dusk.money.dto.response;

import com.dusk.money.dto.PriceVal;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Price(
        int id,
        String name,
        PriceVal priceBuy,
        PriceVal priceSell,
        String variation,
        String volume,
        Date timestamp,
        String spread) {
    public Price(int id, String name, PriceVal priceBuy, PriceVal priceSell, Date timestamp) {
        this(id, name, priceBuy, priceSell, null, null, timestamp, null);
    }
}
