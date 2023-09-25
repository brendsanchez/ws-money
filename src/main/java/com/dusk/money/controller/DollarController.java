package com.dusk.money.controller;

import com.dusk.money.dto.response.DollarTodayDto;
import com.dusk.money.dto.response.MoneyResponse;
import com.dusk.money.scraping.model.Price;
import com.dusk.money.service.DollarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/money")
public class DollarController {

    private final DollarService dollarService;

    @Autowired
    public DollarController(DollarService dollarService) {
        this.dollarService = dollarService;
    }

    @Operation(summary = "get dollar prices.", tags = {"dollar"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 404, \"message\": \"not found prices\"}"))),
            @ApiResponse(responseCode = "500", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 500, \"message\": \"error intern\"}"))),
    })
    @GetMapping
    public MoneyResponse<DollarTodayDto> getDollarPrices() {
        return this.dollarService.getDollarPrices();
    }
}
