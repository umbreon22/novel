package novel.internal.util;

import java.util.function.Consumer;

public final class Ignore {

    private Ignore(){ throw new UnsupportedOperationException("Please do not instantiate me :("); }

    /**
     * An empty method that takes a {@link Throwable} and does nothing!
     */
    public static void completely(Throwable ignored) {}

    /**
     * Prints a stack trace to {@link System#err}
     * @param throwable a {@link Throwable} instance
     */
    public static void butPrintErr(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    /**
     * Attempts to call {@link Throwing.Supplier#get()}...
     * if a {@link Throwable} is caught, we instead use {@code alternate}
     */
    public static <S, T extends Throwable> S andSupplyWithDefault(Throwing.Supplier<S, T> supplier, S alternate) {
        try {
            return supplier.get();
        } catch (Throwable ex) {
            return alternate;
        }
    }

    /**
     * A safe run or else on catch utility method
     * @param func a {@link Throwing.Runnable} instance to attempt running
     * @param log a {@link Consumer} to accept if a {@link Throwable} is caught
     */
    public static <C> void andAcceptOrElse(Throwing.Consumer<C, ?> func, C consumable, Consumer<Throwable> log) {
        try {
            func.accept(consumable);
        } catch (Throwable ex) {
            log.accept(ex);
        }
    }

    /**
     * A safe run or else on catch utility method
     * @param func a {@link Throwing.Runnable} instance to attempt running
     * @param log a {@link Consumer} to accept if a {@link Throwable} is caught
     */
    public static void andRunOrElse(Throwing.Runnable<?> func, Consumer<Throwable> log) {
        try {
            func.run();
        } catch (Throwable ex) {
            log.accept(ex);
        }
    }
}
