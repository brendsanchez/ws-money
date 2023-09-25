package com.dusk.money.scraping.dolarito;

import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.model.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Dolarito implements Dollar {

    @Value("${url.dolarito}")
    private String route;

    @Override
    public List<Price> prices() {
        return new ArrayList<>();
    }

}
