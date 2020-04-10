package novel.internal.adapters;

import java.util.Currency;

public class CurrencyAdapter extends ObjectToStringDataAdapter<Currency> {
    CurrencyAdapter(){
        super(Currency::getInstance);
    }
}
