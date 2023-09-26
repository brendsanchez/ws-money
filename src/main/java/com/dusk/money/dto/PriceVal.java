package com.dusk.money.dto;

import java.math.BigDecimal;

public record PriceVal(
        String valText,
        BigDecimal val) {
}
