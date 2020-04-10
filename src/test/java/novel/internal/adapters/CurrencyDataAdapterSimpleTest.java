package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.util.Currency;
import java.util.Locale;

class CurrencyDataAdapterSimpleTest extends SimpleGoodBadTest<Currency> {
    CurrencyDataAdapterSimpleTest() {
        super(new CurrencyAdapter(), Currency.getInstance(Locale.US), stream->stream.strings("owo whats this?"));
    }

    void testWithBadLocale() {
        //todo.. could test with lots of others
    }

}
