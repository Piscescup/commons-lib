package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.interfaces.exfunction.BinFunction;
import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents a function that accepts two arguments and produces a {@code byte}-valued result.
 *
 * <p>This is the {@code byte}-producing primitive specialization for {@link BinFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsByte(Object, Object)}.
 *
 * @param <X1> the type of the first argument
 * @param <X2> the type of the second argument
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ToByteBinFunction<X1, X2> {

    /**
     * Applies this function to the given arguments.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @return the function result
     */
    byte applyAsByte(X1 x1, X2 x2);

    /**
     * Applies this function to the given arguments and returns the result as a {@link Byte} object.
     *
     * @param x1 the first argument
     * @param x2 the second argument
     * @return the function result as a {@link Byte} object
     * @throws NullPointerException if {@code x1} or {@code x2} is {@code null}
     */
    default Byte boxedApply(X1 x1, X2 x2) {
        NullCheck.requireNonNull(x1);
        NullCheck.requireNonNull(x2);
        return applyAsByte(x1, x2);
    }
}