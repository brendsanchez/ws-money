package com.dusk.money.scraping;

import com.dusk.money.dto.response.Price;

import java.util.List;

public interface Dollar {

    List<Price> prices();

}
