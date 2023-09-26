package com.dusk.money.service;

import com.dusk.money.dto.response.MoneyResponse;
import com.dusk.money.enums.Web;
import com.dusk.money.scraping.model.Price;

import java.util.List;

public interface DollarService {
    MoneyResponse<List<Price>> getDollarPrices(Web web);
}
