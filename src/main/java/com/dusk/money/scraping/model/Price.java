package com.dusk.money.scraping.model;

public record Price(
        String name,
        PriceVal priceCompra,
        PriceVal priceVenta) {
}
