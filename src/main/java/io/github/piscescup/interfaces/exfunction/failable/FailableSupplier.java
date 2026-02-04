package io.github.piscescup.interfaces.exfunction.failable;

/**
 * Represents a supplier of results that may throw an exception.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <em>failable</em> specialization of {@link java.util.function.Supplier}
 * whose functional method is {@link #getOrThrow()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception that may be thrown
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableSupplier<T, E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     * @throws E if unable to supply a result
     */
    T getOrThrow() throws E;
}
