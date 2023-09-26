package com.dusk.money.scraping.dolarito;

import com.dusk.money.dto.Price;
import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.DollarElement;
import com.dusk.money.utils.UtilMoney;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Component
public class Dolarito implements Dollar, DollarElement {

    @Value("${url.dolarito}")
    private String route;

    @Override
    public List<Price> prices() {
        return new ArrayList<>();
    }

    @Override
    public Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstDiv = document.getElementsByClass("tile dolar").getFirst();
        Assert.notNull(firstDiv, "don't found title dolar class");
        return firstDiv;
    }
}
