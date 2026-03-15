package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.QuadFunction;

/**
 * Represents a function that accepts four int-valued arguments and produces a
 * result. This is the {@code int}-consuming primitive specialization for
 * {@link QuadFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(int, int, int, int)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see QuadFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntQuadFunction<R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param value1 the first function argument
     * @param value2 the second function argument
     * @param value3 the third function argument
     * @param value4 the fourth function argument
     * @return the function result
     */
    R apply(int value1, int value2, int value3, int value4);
}
