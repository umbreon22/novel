package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.util.Currency;
import java.util.Locale;

class CurrencyAdapterSimpleTest extends SimpleGoodBadTest<Currency> {
    CurrencyAdapterSimpleTest() {
        super(new CurrencyAdapter(), Currency.getInstance(Locale.US), stream->stream.strings("owo whats this?"));
    }

    void testWithBadLocale() {
        //todo.. could test with lots of others
    }

}
