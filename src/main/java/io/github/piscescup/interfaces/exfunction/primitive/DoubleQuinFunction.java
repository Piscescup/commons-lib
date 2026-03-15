package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.QuinFunction;

/**
 * Represents a function that accepts five double-valued arguments and produces a
 * result. This is the {@code double}-consuming primitive specialization for
 * {@link QuinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(double, double, double, double, double)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see QuinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface DoubleQuinFunction<R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param value1 the first function argument
     * @param value2 the second function argument
     * @param value3 the third function argument
     * @param value4 the fourth function argument
     * @param value5 the fifth function argument
     * @return the function result
     */
    R apply(double value1, double value2, double value3, double value4, double value5);
}
