package io.github.piscescup.interfaces.exfunction;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts two arguments and produces an {@code int}-valued result.
 *
 * <p>This is the {@code int}-producing primitive specialization for {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(Object, Object)}.
 *
 * @param <X1> the type of the first argument
 * @param <X2> the type of the second argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToIntBinFunction<X1, X2> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @return the function result
     */
    int applyAsInt(X1 x1, X2 x2);

    /**
     * Applies this function to the given arguments and returns the result as an {@link Integer} object.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @return the function result as an {@link Integer} object
     * @throws NullPointerException if {@code x1} or {@code x2} is {@code null}
     */
    default Integer boxedApply(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        return applyAsInt(x1, x2);
    }
}