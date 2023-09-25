package com.dusk.money.scraping.model;

import java.math.BigDecimal;

public record PriceVal(
        String valText,
        BigDecimal val) {
}
