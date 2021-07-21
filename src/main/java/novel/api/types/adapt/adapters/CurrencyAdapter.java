package novel.api.types.adapt.adapters;

import java.util.Currency;

public class CurrencyAdapter extends ObjectToStringAdapter<Currency> {
    public CurrencyAdapter(){
        super(Currency::getInstance);
    }
}
