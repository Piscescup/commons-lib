package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.TriFunction;

/**
 * Represents a function that accepts three short-valued arguments and produces a
 * result. This is the {@code short}-consuming primitive specialization for
 * {@link TriFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(short, short, short)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see TriFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortTriFunction<R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param value1 the first function argument
     * @param value2 the second function argument
     * @param value3 the third function argument
     * @return the function result
     */
    R apply(short value1, short value2, short value3);
}
