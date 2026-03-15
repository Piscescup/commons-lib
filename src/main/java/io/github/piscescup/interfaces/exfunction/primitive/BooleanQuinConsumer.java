package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts five {@code boolean}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(boolean, boolean, boolean, boolean, boolean)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface BooleanQuinConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @param x5 the fifth input argument
     */
    void accept(boolean x1, boolean x2, boolean x3, boolean x4, boolean x5);

    /**
     * Returns a composed {@code BooleanQuinConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BooleanQuinConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default BooleanQuinConsumer andThen(BooleanQuinConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            accept(x1, x2, x3, x4, x5);
            after.accept(x1, x2, x3, x4, x5);
        };
    }

}
