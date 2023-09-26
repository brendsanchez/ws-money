package com.dusk.money.utils;

import com.dusk.money.dto.PriceVal;
import com.dusk.money.enums.SpecialCharacter;
import com.dusk.money.exception.GenericMoneyException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public final class UtilMoney {

    public static final int CANT_INIT = 1;

    private UtilMoney() {
        throw new UnsupportedOperationException("This is a UtilDocument class and cannot be instantiated");
    }

    public static Document getFromUri(String uri) {
        try {
            Connection conn = Jsoup.connect(uri);
            Document document = conn.get();

            var statusResponse = HttpStatus.valueOf(conn.response().statusCode());
            Assert.isTrue(statusResponse.equals(HttpStatus.OK), "not get document");
            return document;
        } catch (Exception ex) {
            throw new GenericMoneyException(ex.getMessage());
        }
    }

    public static PriceVal getPriceVal(String priceText) {
        if (priceText.isEmpty()) {
            return null;
        }

        String valText = SpecialCharacter.MONEY.getCharacter() + priceText;
        BigDecimal val = new BigDecimal(priceText);
        return new PriceVal(valText, val);
    }
}
