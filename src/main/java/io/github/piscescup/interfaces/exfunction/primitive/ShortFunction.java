package io.github.piscescup.interfaces.exfunction.primitive;

import java.util.function.Function;

/**
 * Represents a function that accepts a short-valued argument and produces a
 * result. This is the {@code short}-consuming primitive specialization for
 * {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(short)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see Function
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(short value);
}
