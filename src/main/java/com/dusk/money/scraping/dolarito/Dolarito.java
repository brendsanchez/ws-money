package com.dusk.money.scraping.dolarito;

import com.dusk.money.dto.PriceVal;
import com.dusk.money.dto.response.Price;
import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.DollarElement;
import com.dusk.money.utils.UtilMoney;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Dolarito implements Dollar, DollarElement {

    @Value("${url.dolarito}")
    private String route;

    @Override
    public List<Price> prices() {
        Element element = this.element();
        String jsonText = element.html();

        JSONObject jsonObject = new JSONObject(jsonText);
        JSONObject quotations = jsonObject.getJSONObject("props")
                .getJSONObject("pageProps")
                .getJSONObject("realTimeQuotations")
                .getJSONObject("quotations");

        List<Price> prices = new ArrayList<>();
        int cant = UtilMoney.CANT_INIT;
        for (String key : quotations.keySet()) {
            JSONObject quotationJson = quotations.getJSONObject(key);
            Price quotation = this.priceFromJson(cant, quotationJson);
            prices.add(quotation);
            cant++;
        }
        return prices;
    }

    private Price priceFromJson(int index, JSONObject jsonObject) {
        return new Price(
                index,
                jsonObject.optString("name"),
                null,
                this.getPriceValJson(jsonObject.optString("buy")),
                this.getPriceValJson(jsonObject.optString("sell")),
                jsonObject.optString("variation"),
                jsonObject.optString("volumen"),
                new Date(jsonObject.optLong("timestamp")),
                jsonObject.optString("spread")
        );
    }

    private PriceVal getPriceValJson(String priceText) {
        String priceValid = priceText.replace(",", ".");
        return UtilMoney.getPriceVal(priceValid);
    }

    @Override
    public Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstDiv = document.getElementById("__NEXT_DATA__");
        Assert.notNull(firstDiv, "don't found next_data id");
        return firstDiv;
    }
}
