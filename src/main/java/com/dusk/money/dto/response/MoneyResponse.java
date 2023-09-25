package com.dusk.money.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record MoneyResponse<T>(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "200", description = "Código de respuesta del ws-money")
        int code,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "success", description = "Descripción del código de respuesta del ws-money")
        String message,

        T data) {
}
