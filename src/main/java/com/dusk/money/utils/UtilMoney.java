package com.dusk.money.utils;

import com.dusk.money.exception.GenericMoneyException;
import com.dusk.money.scraping.model.PriceVal;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class UtilMoney {
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

    public static PriceVal getPriceVal(String text) {
        String valText = replaceAndTrim(text, "\"");
        BigDecimal val = removeCharMoney(valText);
        return new PriceVal(valText, val);
    }

    public static String getUpdatedFromText(String text) {
        return replaceAndTrim(text, "Actualizado el ");
    }

    private static String replaceAndTrim(String field, String target) {
        return field.replace(target, Strings.EMPTY).trim();
    }

    private static BigDecimal removeCharMoney(String field) {
        String value = field.replace("$", Strings.EMPTY);
        return new BigDecimal(value);
    }
}
