package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.QuadFunction;
import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts four arguments and produces a {@code char}-valued result.
 *
 * <p>This is the {@code char}-producing primitive specialization for {@link QuadFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsChar(Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument
 * @param <X2> the type of the second argument
 * @param <X3> the type of the third argument
 * @param <X4> the type of the fourth argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToCharQuadFunction<X1, X2, X3, X4> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @param x3 the third argument
     * @param x4 the fourth argument
     * @return the function result
     */
    char applyAsChar(X1 x1, X2 x2, X3 x3, X4 x4);

    /**
     * Applies this function to the given arguments and returns the result as a {@link Character} object.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @param x3 the third argument
     * @param x4 the fourth argument
     * @return the function result as a {@link Character} object
     * @throws NullPointerException if {@code x1}, {@code x2}, {@code x3} or {@code x4} is {@code null}
     */
    default Character boxedApply(X1 x1, X2 x2, X3 x3, X4 x4) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        NullCheck.requireNonNull(x4);
        return applyAsChar(x1, x2, x3, x4);
    }
}
