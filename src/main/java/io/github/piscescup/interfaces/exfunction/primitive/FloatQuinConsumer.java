package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts five {@code float}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(float, float, float, float, float)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface FloatQuinConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @param x5 the fifth input argument
     */
    void accept(float x1, float x2, float x3, float x4, float x5);

    /**
     * Returns a composed {@code FloatQuinConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FloatQuinConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default FloatQuinConsumer andThen(FloatQuinConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            accept(x1, x2, x3, x4, x5);
            after.accept(x1, x2, x3, x4, x5);
        };
    }

}