package novel.internal.adapters;

import java.util.Currency;

public class CurrencyAdapter extends ObjectToStringAdapter<Currency> {
    CurrencyAdapter(){
        super(Currency::getInstance);
    }
}
