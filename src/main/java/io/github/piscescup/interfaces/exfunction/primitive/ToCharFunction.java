package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts one argument and produces a {@code char}-valued result.
 *
 * <p>This is the {@code char}-producing primitive specialization for {@link java.util.function.Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsChar(Object)}.
 *
 * @param <X> the type of the argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToCharFunction<X> {

    /**
     * Applies this function to the given argument.
     *
     * @param x the argument
     * @return the function result
     */
    char applyAsChar(X x);

    /**
     * Applies this function to the given argument and returns the result as a {@link Character} object.
     *
     * @param x the argument
     * @return the function result as a {@link Character} object
     * @throws NullPointerException if {@code x} is {@code null}
     */
    default Character boxedApply(X x) {
        NullCheck.requireNonNull(x);
        return applyAsChar(x);
    }
}