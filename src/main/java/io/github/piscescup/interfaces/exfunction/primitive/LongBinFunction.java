package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.BinFunction;

/**
 * Represents a function that accepts two char-valued arguments and produces a
 * result. This is the {@code char}-consuming primitive specialization for
 * {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong}.
 *
 * @param <R> the type of the result of the function
 *
 * @see BinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongBinFunction<R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @return the result of the function
     */
    R applyAsLong(long x1, long x2);
}
