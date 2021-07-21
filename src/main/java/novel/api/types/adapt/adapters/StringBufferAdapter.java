package novel.api.types.adapt.adapters;

public class StringBufferAdapter extends ObjectToStringAdapter<StringBuffer> {
    public StringBufferAdapter() {
        super(StringBuffer::new);
    }
}
