package novel.internal.adapters;

public class StringBuilderAdapter extends ObjectToStringDataAdapter<StringBuilder> {
    StringBuilderAdapter() {
        super(StringBuilder::new);
    }
}
