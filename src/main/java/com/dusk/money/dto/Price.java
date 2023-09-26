package com.dusk.money.dto;

public record Price(
        String name,
        String lastUpdated,
        PriceVal priceBuy,
        PriceVal priceSell,
        String variation) {
    public Price(String name, String lastUpdated, PriceVal priceBuy, PriceVal priceSell) {
        this(name, lastUpdated, priceBuy, priceSell, null);
    }
}
