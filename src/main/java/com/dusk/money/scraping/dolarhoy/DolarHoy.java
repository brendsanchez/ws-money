package com.dusk.money.scraping.dolarhoy;

import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.model.Price;
import com.dusk.money.scraping.model.PriceVal;
import com.dusk.money.utils.UtilMoney;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Component
public class DolarHoy implements Dollar {

    @Value("${url.dolarhoy}")
    private String route;

    @Override
    public List<Price> prices() {
        List<Price> prices = new ArrayList<>();

        Price dollarBlue = this.dollarBlue();
        List<Price> restPrices = this.restPrices();

        prices.add(dollarBlue);
        prices.addAll(restPrices);
        return prices;
    }

    @Override
    public Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstDiv = document.getElementsByClass("tile dolar").getFirst();
        Assert.notNull(firstDiv, "don't found title dolar class");
        return firstDiv;
    }

    private Price dollarBlue() {
        Element nameElement = this.element().select("div a").first();
        Assert.notNull(nameElement, "don't found name element");

        Element compraElement = this.element().select("div.values div.compra div.val").getFirst();
        Assert.notNull(compraElement, "don't found compra element");

        Element ventaElement = this.element().select("div.values div.venta div.val").getFirst();
        Assert.notNull(ventaElement, "don't found venta element");

        Element lastUpdatedElement = this.element().select("div.tile.update span").getFirst();
        Assert.notNull(lastUpdatedElement, "don't found venta element");

        PriceVal compraVal = UtilMoney.getPriceVal(compraElement.text());
        PriceVal ventaVal = UtilMoney.getPriceVal(ventaElement.text());
        String updated = UtilMoney.getUpdatedFromText(lastUpdatedElement.text());
        return new Price(nameElement.text(),
                updated,
                compraVal,
                ventaVal);
    }

    private List<Price> restPrices() {
        Elements pricesElement = this.element().select("div.tile.is-parent.is-7.is-vertical");
        Assert.notNull(pricesElement, "don't found the rest dollars");

        List<Price> prices = new ArrayList<>();
        for (Element el : pricesElement) {
            String name = el.text();
            //BigDecimal compra = new BigDecimal("");
            //BigDecimal venta = new BigDecimal("");
            System.out.println(el);
            Price price = new Price(name, null, null, null);
            prices.add(price);
        }
        return prices;
    }
}
