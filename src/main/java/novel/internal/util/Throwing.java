package novel.internal.util;

public final class Throwing {

    private Throwing() { throw new UnsupportedOperationException("Please do not instantiate me :("); }

    /**
     * @see java.lang.Runnable
     * @param <T> a {@link Throwable}
     */
    @FunctionalInterface
    public interface Runnable<T extends Throwable> {
        void run() throws T;
    }

    /**
     * @see java.util.function.Consumer
     * @param <C> a consumable
     * @param <T> a {@link Throwable}
     */
    @FunctionalInterface
    public interface Consumer<C, T extends Throwable> {
        void accept(C c) throws T;
    }

    /**
     * @see java.util.function.Supplier
     * @param <R> a return type
     * @param <T> a {@link Throwable}
     */
    @FunctionalInterface
    public interface Supplier<R, T extends Throwable> {
        R get() throws T;
    }
}
