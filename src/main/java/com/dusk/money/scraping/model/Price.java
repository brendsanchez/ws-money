package com.dusk.money.scraping.model;

public record Price(
        String name,
        String updated,
        PriceVal priceCompra,
        PriceVal priceVenta) {
}
