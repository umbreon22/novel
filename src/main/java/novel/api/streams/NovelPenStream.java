package novel.api.streams;

import novel.internal.util.Ignore;
import novel.api.types.write.pens.DataPen;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Meant to be used in conjunction with {@link NovelPaperStream}
 * Wraps an {@link OutputStream} with {@link DataPen} capability.
 * todo: move to its own module?
 */
public class NovelPenStream extends ObjectOutputStream implements DataPen<NovelPenStream> {

    public NovelPenStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    public NovelPenStream bools(boolean b) {
        Ignore.andRunOrElse(()->this.writeBoolean(b), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream ints(int i) {
        Ignore.andRunOrElse(()->this.writeInt(i), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream longs(long l) {
        Ignore.andRunOrElse(()->this.writeLong(l), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream shorts(short s) {
        Ignore.andRunOrElse(()->this.writeShort(s), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream floats(float f) {
        Ignore.andRunOrElse(()->this.writeFloat(f), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream doubles(double d) {
        Ignore.andRunOrElse(()->this.writeDouble(d), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream strings(CharSequence str) {
        Ignore.andRunOrElse(()->{
            this.writeShort(str.length());
            str.chars().forEach(c->this.chars((char)c));
        }, Ignore::butPrintErr);
        return this;
    }

    @Override
    public NovelPenStream chars(char c) {
        Ignore.andRunOrElse(()->this.writeChar(c), Ignore::completely);
        return this;
    }

    @Override
    public NovelPenStream bytes(byte b) {
        Ignore.andRunOrElse(()->this.writeByte(b), Ignore::completely);
        return this;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
