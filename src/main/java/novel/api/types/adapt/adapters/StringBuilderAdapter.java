package novel.api.types.adapt.adapters;

public class StringBuilderAdapter extends ObjectToStringAdapter<StringBuilder> {
    public StringBuilderAdapter() {
        super(StringBuilder::new);
    }
}
