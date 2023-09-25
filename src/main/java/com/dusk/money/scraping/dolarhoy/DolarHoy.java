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
        Element pricesElement = this.element().select("div.tile.is-parent.is-7.is-vertical").getFirst();
        Assert.notNull(pricesElement, "don't found the rest dollars");

        Elements children = pricesElement.children();
        return this.getChildrenPrices(children);
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
                compraVal,
                ventaVal);
    }

    @Override
    public String lastUpdated() {
        Element lastUpdatedElement = this.element().select("div.tile.update span").getFirst();
        Assert.notNull(lastUpdatedElement, "don't found updated element");

        return UtilMoney.getUpdatedFromText(lastUpdatedElement.text());
    }

    private List<Price> getChildrenPrices(Elements children) {
        List<Price> childrenPrices = new ArrayList<>();

        for (Element child : children) {
            if (this.isChildPrice(child)) {
                continue;
            }
            Price price = this.getPriceFromChildWithUpdated(child);
            childrenPrices.add(price);
        }
        return childrenPrices;
    }

    private boolean isChildPrice(Element child) {
        return child.children().isEmpty() || child.children().size() < 2;
    }

    private Price getPriceFromChildWithUpdated(Element element) {
        String name = element.child(0).text();
        String value = element.child(1).text();

        return new Price(name, null, null);
    }

    private Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstDiv = document.getElementsByClass("tile dolar").getFirst();
        Assert.notNull(firstDiv, "don't found title dolar class");
        return firstDiv;
    }
}
