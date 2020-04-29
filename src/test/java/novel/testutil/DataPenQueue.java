package novel.testutil;

import novel.api.types.write.pens.DataPen;

import java.util.LinkedList;
import java.util.Queue;

public class DataPenQueue implements DataPen<DataPenQueue> {

    private final Queue<Object> queue = new LinkedList<>();

    public Queue<Object> queue() {
        return queue;
    }

    public DataPaperQueue paper() {
        return new DataPaperQueue(queue);
    }

    @Override
    public DataPenQueue bools(boolean b) {
        queue.add(b);
        return this;
    }

    @Override
    public DataPenQueue ints(int i) {
        queue.add(i);
        return this;
    }

    @Override
    public DataPenQueue longs(long l) {
        queue.add(l);
        return this;
    }

    @Override
    public DataPenQueue shorts(short s) {
        queue.add(s);
        return this;
    }

    @Override
    public DataPenQueue floats(float f) {
        queue.add(f);
        return this;
    }

    @Override
    public DataPenQueue doubles(double d) {
        queue.add(d);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence str) {
        queue.add(str);
        return this;
    }

    @Override
    public DataPenQueue chars(char c) {
        queue.add(c);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte b) {
        queue.add(b);
        return this;
    }
}
