package com.dusk.money.scraping.dolarhoy;

import com.dusk.money.dto.PriceVal;
import com.dusk.money.dto.response.Price;
import com.dusk.money.enums.SpecialCharacter;
import com.dusk.money.exception.GenericMoneyException;
import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.DollarElement;
import com.dusk.money.utils.UtilMoney;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class DolarHoy implements Dollar, DollarElement {

    private Date lastUpdated;

    @Value("${url.dolarhoy}")
    private String route;

    @Override
    public List<Price> prices() {
        Element pricesElement = this.element().select("div.tile.is-parent.is-7.is-vertical").getFirst();
        Assert.notNull(pricesElement, "don't found the rest dollars");

        lastUpdated = this.lastUpdated();

        Elements children = pricesElement.children();
        List<Price> prices = new ArrayList<>();
        int cant = UtilMoney.CANT_INIT;
        for (Element child : children) {
            if (this.nonValidChild(child)) {
                continue;
            }

            Price price = this.getPriceFromChildWithUpdated(cant, child);
            prices.add(price);
            cant++;
        }
        return prices;
    }

    @Override
    public Element element() {
        Document document = UtilMoney.getFromUri(route);
        Element firstDiv = document.getElementsByClass("tile dolar").getFirst();
        Assert.notNull(firstDiv, "don't found title dolar class");
        return firstDiv;
    }

    public Date lastUpdated() {
        Element lastUpdatedElement = this.element().select("div.tile.update span").getFirst();
        Assert.notNull(lastUpdatedElement, "don't found updated element");

        String lastUpdatedText = this.replaceUpdated(lastUpdatedElement.text());
        if (lastUpdatedText.isEmpty()) {
            return null;
        }

        try {
            final SimpleDateFormat formatPage = new SimpleDateFormat("dd/MM/yy hh:mm a", Locale.ROOT);
            return formatPage.parse(lastUpdatedText);
        } catch (ParseException ex) {
            throw new GenericMoneyException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean nonValidChild(Element child) {
        return child.children().isEmpty() || child.children().size() < 2;
    }

    private Price getPriceFromChildWithUpdated(int index, Element element) {
        String name = element.child(0).text();
        Elements priceElement = element.child(1).children();
        List<PriceVal> priceValues = this.priceValues(priceElement);
        return new Price(index, name, priceValues.getFirst(), priceValues.getLast(), this.lastUpdated);
    }

    private List<PriceVal> priceValues(Elements valChildren) {
        List<PriceVal> priceValues = new ArrayList<>(2);
        for (Element element : valChildren) {
            String priceText = this.getPriceFromText(element.wholeText());
            PriceVal priceVal = UtilMoney.getPriceVal(priceText);
            priceValues.add(priceVal);
        }
        return priceValues;
    }

    private String getPriceFromText(String text) {
        return text.substring(text.indexOf(SpecialCharacter.MONEY.getCharacter()) + 1);
    }

    private String replaceUpdated(String field) {
        return field.replace("Actualizado el ", Strings.EMPTY).trim();
    }
}
