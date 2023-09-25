package com.dusk.money.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record MoneyDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "cant countries")
        String data,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "all the names of the countries")
        String info) {
}
