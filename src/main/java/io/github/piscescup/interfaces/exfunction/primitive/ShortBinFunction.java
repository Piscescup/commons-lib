package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.BinFunction;

/**
 * Represents a function that accepts two short-valued arguments and produces a
 * result. This is the {@code short}-consuming primitive specialization for
 * {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(short, short)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see BinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortBinFunction<R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param value1 the first function argument
     * @param value2 the second function argument
     * @return the function result
     */
    R apply(short value1, short value2);
}
