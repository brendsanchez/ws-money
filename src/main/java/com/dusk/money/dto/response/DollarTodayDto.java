package com.dusk.money.dto.response;


import com.dusk.money.scraping.model.Price;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record DollarTodayDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "cant countries")
        String updated,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "all the names of the countries")
        List<Price> prices) {
}
