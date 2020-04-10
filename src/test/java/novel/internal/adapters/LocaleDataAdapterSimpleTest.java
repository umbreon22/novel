package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import novel.api.types.read.DataPaperReadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;

class LocaleDataAdapterSimpleTest extends SimpleGoodBadTest<Locale> {

    LocaleDataAdapterSimpleTest() {
        super(new LocaleAdapter(), Locale.ITALIAN, stream-> {//todo
            throw new DataPaperReadException("I am not sure how to break the Locale constructor! Let's revisit.");
        });
    }

    void testLocale(Locale locale) throws IOException {
        LocaleAdapter adapter = new LocaleAdapter();
        byte[] input = withOutputStream(stream->adapter.write(stream, locale));
        Locale readLocale = withInputStream(input, adapter::read);
        Assertions.assertEquals(locale, readLocale);
    }

    @Test
    void testLanguage() throws IOException {
        testLocale(new Locale("uhhh"));
    }

    @Test
    void testLanguageAndCountry() throws IOException {
        testLocale(new Locale("uhhh", "umm"));
    }

    @Test
    void testLanguageCountryAndVariant() throws IOException {
        testLocale(new Locale("uhhh", "umm", "okayy"));
    }

}
