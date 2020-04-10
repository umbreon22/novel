package novel.api.streams;

import novel.api.types.read.DataPaperReadException;
import novel.internal.util.Ignore;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Meant to be used in conjunction with {@link NovelPenStream}
 * Wraps a {@link InputStream} with {@link DataPaper} capability.
 * todo: move to its own module?
 */
public class NovelPaperStream extends ObjectInputStream implements DataPaper {

    public NovelPaperStream(InputStream in) throws IOException {
        super(in);
    }

    public NovelPaperStream(byte[] bytes) throws IOException {
        super(new ByteArrayInputStream(bytes));
    }

    @Override
    public void close() throws IOException {
        if(available() != 0) {
            throw new DataPaperReadException(available() + " bytes of data was not read.");
        }
        super.close();
    }

    @Override
    public int ints() {
        return Ignore.andSupplyWithDefault(this::readInt, Integer.MIN_VALUE);
    }

    @Override
    public short shorts() {
        return Ignore.andSupplyWithDefault(this::readShort, Short.MIN_VALUE);
    }

    @Override
    public long longs() {
        return Ignore.andSupplyWithDefault(this::readLong, Long.MIN_VALUE);
    }

    @Override
    public String strings() {
        return Ignore.andSupplyWithDefault(()->{
            short len = this.readShort();
            if(len == 0) return "";
            char[] buf = new char[len];
            for(int i = 0; i < len; i++) {
                buf[i] = this.readChar();
            }
            return new String(buf);
        }, "Unable to read string from DataInputStream!");
    }

    @Override
    public boolean bools() {
        return Ignore.andSupplyWithDefault(this::readBoolean, false);
    }

    @Override
    public double doubles() {
        return Ignore.andSupplyWithDefault(this::readDouble, Double.MIN_NORMAL);
    }

    @Override
    public float floats() {
        return Ignore.andSupplyWithDefault(this::readFloat, Float.MIN_NORMAL);
    }

    @Override
    public byte bytes() {
        return Ignore.andSupplyWithDefault(this::readByte, Byte.MIN_VALUE);
    }

    @Override
    public char chars() {
        return Ignore.andSupplyWithDefault(this::readChar, Character.MIN_VALUE);
    }

    @Override
    public <T> T objects(ObjectDataReader<T> reader) {
        return reader.read(this);
    }

    @Override
    public void skip(int byteSize) {
        Ignore.andRunOrElse(()->this.skipNBytes(byteSize), Ignore::completely);
    }


}
