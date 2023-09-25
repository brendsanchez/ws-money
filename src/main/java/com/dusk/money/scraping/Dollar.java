package com.dusk.money.scraping;

import com.dusk.money.scraping.model.Price;
import org.jsoup.nodes.Element;

import java.util.List;

public interface Dollar {

    List<Price> prices();

    Element element();
}
