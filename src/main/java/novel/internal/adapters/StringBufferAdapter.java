package novel.internal.adapters;

public class StringBufferAdapter extends ObjectToStringDataAdapter<StringBuffer> {
    StringBufferAdapter() {
        super(StringBuffer::new);
    }
}
