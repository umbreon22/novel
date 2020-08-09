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
        if(queue.peek() instanceof Integer) {
            return (int) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll ints");
        }
    }

    @Override
    public short shorts() {
        if(queue.peek() instanceof Short) {
            return (short) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll shorts");
        }
    }

    @Override
    public long longs() {
        if(queue.peek() instanceof Long) {
            return (long) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll longs");
        }
    }

    @Override
    public String strings() {
        if(queue.peek() instanceof String) {
            return (String) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll strings");
        }
    }

    @Override
    public boolean bools() {
        if(queue.peek() instanceof Boolean) {
            return (boolean) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll bools");
        }
    }

    @Override
    public double doubles() {
        if(queue.peek() instanceof Double) {
            return (double) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll doubles");
        }
    }

    @Override
    public float floats() {
        if(queue.peek() instanceof Float) {
            return (float) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll floats");
        }
    }

    @Override
    public byte bytes() {
        if(queue.peek() instanceof Byte) {
            return (byte) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll bytes");
        }
    }

    @Override
    public char chars() {
        if(queue.peek() instanceof Character) {
            return (char) queue.poll();
        } else {
            throw new IllegalStateException("Cannot poll bytes");
        }
    }

    @Override
    public void skip(int byteSize) {
        throw new UnsupportedOperationException("Cannot skip");
    }

}
