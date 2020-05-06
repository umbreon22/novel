package novel.internal.adapters;

public class StringBuilderAdapter extends ObjectToStringAdapter<StringBuilder> {
    StringBuilderAdapter() {
        super(StringBuilder::new);
    }
}
