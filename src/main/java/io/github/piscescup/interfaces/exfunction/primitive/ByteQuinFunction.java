package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.QuinFunction;

/**
 * Represents a function that accepts five byte-valued arguments and produces a
 * result. This is the {@code byte}-consuming primitive specialization for
 * {@link QuinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(byte, byte, byte, byte, byte)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see QuinFunction
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteQuinFunction<R> {

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
    R apply(byte value1, byte value2, byte value3, byte value4, byte value5);
}