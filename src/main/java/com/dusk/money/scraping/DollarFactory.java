package com.dusk.money.scraping;

import com.dusk.money.enums.Web;
import com.dusk.money.scraping.dolarhoy.DolarHoy;
import com.dusk.money.scraping.dolarito.Dolarito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DollarFactory {

    private final DolarHoy dolarHoy;
    private final Dolarito dolarito;

    @Autowired
    public DollarFactory(DolarHoy dolarHoy, Dolarito dolarito) {
        this.dolarHoy = dolarHoy;
        this.dolarito = dolarito;
    }

    public Dollar getDollar(Web web) {
        if (Web.DOLAR_HOY.equals(web)) {
            return dolarHoy;
        }

        if (Web.DOLARITO.equals(web)) {
            return dolarito;
        }
        return dolarito;
    }
}
