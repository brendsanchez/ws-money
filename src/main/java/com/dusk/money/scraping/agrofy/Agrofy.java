package com.dusk.money.scraping.agrofy;

import com.dusk.money.scraping.Dollar;
import com.dusk.money.dto.Price;
import com.dusk.money.utils.UtilMoney;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Component
public class Agrofy implements Dollar {

    @Value("${url.agrofy}")
    private String route;

    @Override
    public List<Price> prices() {
        return new ArrayList<>();
    }

    private Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstBody = document.getElementsByTag("table.table-agrofy.table tbody").getFirst();
        Assert.notNull(firstBody, "don't found table agrofy");
        return firstBody;
    }
}
