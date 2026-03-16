package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.BinFunction;
import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts three arguments and produces a float-valued
 * result.  This is the {@code float}-producing primitive specialization for
 * {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsFloat(Object, Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the function
 * @param <X2> the type of the second argument to the function
 *
 * @see BinFunction
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToFloatQuinFunction<X1, X2, X3, X4, X5> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first function argument
     * @param x2 the second function argument
     * @param x3 the third function argument
     * @param x4 the fourth function argument
     * @param x5 the fifth function argument
     * @return the function result
     */
    float applyAsFloat(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5);

    /**
     * Applies this function to the given arguments and returns the result as a {@link Float} object.
     *
     * @param x1 the first function argument
     * @param x2 the second function argument
     * @param x3 the third function argument
     * @param x4 the fourth function argument
     * @param x5 the fifth function argument
     * @return the function result as a {@link Float} object
     * @throws NullPointerException if {@code x1}, {@code x2}, {@code x3}, {@code x4} or {@code x5} is {@code null}
     */
    default Float boxedApply(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        NullCheck.requireNonNull(x4);
        NullCheck.requireNonNull(x5);

        return applyAsFloat(x1, x2, x3, x4, x5);
    }
}
