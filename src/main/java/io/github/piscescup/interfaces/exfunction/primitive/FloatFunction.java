package io.github.piscescup.interfaces.exfunction.primitive;

import java.util.function.Function;

/**
 * Represents a function that accepts a float-valued argument and produces a
 * result. This is the {@code float}-consuming primitive specialization for
 * {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(float)}.
 *
 * @param <R> the type of the result of the function
 *
 * @see Function
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(float value);
}
