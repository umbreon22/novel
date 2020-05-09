package novel.internal.testutil;

import novel.api.types.read.DataPaper;

import java.util.Queue;

public class DataPaperQueue implements DataPaper {

    private final Queue<Object> queue;

    public DataPaperQueue(Queue<Object> queue) {
        this.queue = queue;
    }

    public Queue<Object> queue() {
        return queue;
    }

    @Override
    public int ints() {
        if(queue.poll() instanceof Integer i) {
            return i;
        } else {
            throw new IllegalStateException("Cannot poll ints");
        }
    }

    @Override
    public short shorts() {
        if(queue.poll() instanceof Short s) {
            return s;
        } else {
            throw new IllegalStateException("Cannot poll shorts");
        }
    }

    @Override
    public long longs() {
        if(queue.poll() instanceof Long l) {
            return l;
        } else {
            throw new IllegalStateException("Cannot poll longs");
        }
    }

    @Override
    public String strings() {
        if(queue.poll() instanceof String str) {
            return str;
        } else {
            throw new IllegalStateException("Cannot poll strings");
        }
    }

    @Override
    public boolean bools() {
        if(queue.poll() instanceof Boolean bool) {
            return bool;
        } else {
            throw new IllegalStateException("Cannot poll bools");
        }
    }

    @Override
    public double doubles() {
        if(queue.poll() instanceof Double d) {
            return d;
        } else {
            throw new IllegalStateException("Cannot poll doubles");
        }
    }

    @Override
    public float floats() {
        if(queue.poll() instanceof Float f) {
            return f;
        } else {
            throw new IllegalStateException("Cannot poll floats");
        }
    }

    @Override
    public byte bytes() {
        if(queue.poll() instanceof Byte b) {
            return b;
        } else {
            throw new IllegalStateException("Cannot poll bytes");
        }
    }

    @Override
    public char chars() {
        if(queue.poll() instanceof Character c) {
            return c;
        } else {
            throw new IllegalStateException("Cannot poll bytes");
        }
    }

    @Override
    public void skip(int byteSize) {
        throw new UnsupportedOperationException("Cannot skip");
    }

}
