package novel.internal.factories;

import novel.api.Novel;
import novel.internal.reflective.TokenableFieldReflectiveReader;
import novel.internal.reflective.TokenableFieldReflectiveWriter;
import novel.internal.reflective.ReflectiveObjectReader;
import novel.internal.reflective.ReflectiveObjectWriter;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.read.Readable;
import novel.api.types.token.TypeToken;
import novel.api.types.write.ObjectDataWriter;
import novel.api.types.write.Writeable;

/**
 * Adapter factory for the {@link novel.internal.reflective} package
 * @see ReflectiveObjectReader
 * @see ReflectiveObjectWriter
 */
public class ReflectiveAdapterFactory extends AdapterFactory {

    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        var rawType = typeToken.getRawType();
        boolean isReadable = Readable.class.isAssignableFrom(rawType);
        boolean isWriteable = Writeable.class.isAssignableFrom(rawType);
        ObjectDataReader<T> reflectiveReader = isReadable
                ? new TokenableFieldReflectiveReader<>(novel, typeToken)
                : null;
        ObjectDataWriter<T> reflectiveWriter = isWriteable
                ? new TokenableFieldReflectiveWriter<>(novel, typeToken)
                : null;
        if (isReadable || isWriteable) {
            return ObjectDataAdapter.tryWrappingAdapter(typeToken, reflectiveReader, reflectiveWriter);
        } else {
            return null;
        }
    }

}
