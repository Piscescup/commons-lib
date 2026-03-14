package io.github.piscescup.interfaces.exfunction;


import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts two arguments and produces a float-valued
 * result.  This is the {@code float}-producing primitive specialization for
 * {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsFloat(Object, Object)}.
 *
 * @param <X1> the type of the first argument to the function
 * @param <X2> the type of the second argument to the function
 *
 * @see BinFunction
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToFloatBinFunction<X1, X2> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first function argument
     * @param x2 the second function argument
     * @return the function result
     */
    float applyAsFloat(X1 x1, X2 x2);

    /**
     * Applies this function to the given arguments and returns the result as a {@link Float} object.
     *
     * @param x1 the first function argument
     * @param x2 the second function argument
     * @return the function result as a {@link Float} object
     * @throws NullPointerException if {@code x1} or {@code x2} is {@code null}
     */
    default Float boxedApply(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);

        return applyAsFloat(x1, x2);
    }
}
