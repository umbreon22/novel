package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.Locale;

public class LocaleAdapter implements ObjectDataAdapter<Locale> {

    LocaleAdapter() {}

    @Override
    public Locale read(DataPaper paper) {
        String language = paper.strings();
        if(!paper.bools()) {
            return new Locale(language);
        }
        String country = paper.strings();
        if(!paper.bools()) {
            return new Locale(language, country);
        }
        String variant = paper.strings();
        return new Locale(language, country, variant);
    }

    @Override
    public void write(DataPen<?> pen, Locale locale) {
        pen.strings(locale.getLanguage());
        if(writeNullableString(pen, locale.getCountry())) {
            writeNullableString(pen, locale.getVariant());
        }
    }

    private static boolean writeNullableString(DataPen<?> pen, String string) {
        if(string != null && !string.isEmpty()) {
            pen.bools(true).strings(string);
            return true;
        } else {
            pen.bools(false);
            return false;
        }
    }
}
