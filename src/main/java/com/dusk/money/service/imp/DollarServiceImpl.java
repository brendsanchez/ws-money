package com.dusk.money.service.imp;

import com.dusk.money.dto.response.MoneyResponse;
import com.dusk.money.enums.Code;
import com.dusk.money.enums.Web;
import com.dusk.money.scraping.Dollar;
import com.dusk.money.scraping.DollarFactory;
import com.dusk.money.dto.Price;
import com.dusk.money.service.DollarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;


@Service
public class DollarServiceImpl implements DollarService {

    private final DollarFactory dollarFactory;

    @Autowired
    public DollarServiceImpl(DollarFactory dollarFactory) {
        this.dollarFactory = dollarFactory;
    }

    private final Logger logger = LoggerFactory.getLogger(DollarServiceImpl.class);

    public MoneyResponse<List<Price>> getDollarPrices(Web web) {
        Dollar dollar = this.dollarFactory.getDollar(web);

        List<Price> prices = dollar.prices();
        logger.debug("prices found:{}", prices.size());

        return new MoneyResponse<>(HttpURLConnection.HTTP_OK,
                Code.SUCCESS.name().toLowerCase(Locale.ROOT),
                prices);
    }
}
