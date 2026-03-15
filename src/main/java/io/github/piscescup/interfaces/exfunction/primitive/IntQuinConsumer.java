package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts five {@code int}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(int, int, int, int, int)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface IntQuinConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @param x5 the fifth input argument
     */
    void accept(int x1, int x2, int x3, int x4, int x5);

    /**
     * Returns a composed {@code IntQuinConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code IntQuinConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default IntQuinConsumer andThen(IntQuinConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            accept(x1, x2, x3, x4, x5);
            after.accept(x1, x2, x3, x4, x5);
        };
    }

}
