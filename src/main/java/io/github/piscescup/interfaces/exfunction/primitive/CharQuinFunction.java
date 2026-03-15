package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.QuinFunction;

/**
 * Represents a function that accepts five char-valued arguments and produces a
 * result. This is the {@code char}-consuming primitive specialization for
 * {@link QuinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(char, char, char, char, char)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see QuinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface CharQuinFunction<R> {

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
    R apply(char value1, char value2, char value3, char value4, char value5);
}
