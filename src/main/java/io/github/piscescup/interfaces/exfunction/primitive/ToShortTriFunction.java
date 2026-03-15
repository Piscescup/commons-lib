package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.TriFunction;
import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts three arguments and produces a {@code short}-valued result.
 *
 * <p>This is the {@code short}-producing primitive specialization for {@link TriFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsShort(Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument
 * @param <X2> the type of the second argument
 * @param <X3> the type of the third argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToShortTriFunction<X1, X2, X3> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @param x3 the third argument
     * @return the function result
     */
    short applyAsShort(X1 x1, X2 x2, X3 x3);

    /**
     * Applies this function to the given arguments and returns the result as a {@link Short} object.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @param x3 the third argument
     * @return the function result as a {@link Short} object
     * @throws NullPointerException if {@code x1}, {@code x2} or {@code x3} is {@code null}
     */
    default Short boxedApply(X1 x1, X2 x2, X3 x3) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        NullCheck.requireNonNull(x3);
        return applyAsShort(x1, x2, x3);
    }
}