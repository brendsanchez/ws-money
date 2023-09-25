package com.dusk.money.utils;

import com.dusk.money.exception.GenericMoneyException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

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


}
