package novel.internal.adapters;

public class StringBufferAdapter extends ObjectToStringAdapter<StringBuffer> {
    StringBufferAdapter() {
        super(StringBuffer::new);
    }
}
