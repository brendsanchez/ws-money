package com.dusk.money.service;

import com.dusk.money.dto.response.DollarTodayDto;
import com.dusk.money.dto.response.MoneyResponse;

public interface DollarService {
    MoneyResponse<DollarTodayDto> getDollarPrices();
}
