package com.dusk.money.scraping.model;

public record Price(
        String name,
        String lastUpdated,
        PriceVal priceBuy,
        PriceVal priceSell) {
}
