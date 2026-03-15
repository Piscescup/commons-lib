package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts four {@code long}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(long, long, long, long)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface LongQuadConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     */
    void accept(long x1, long x2, long x3, long x4);

    /**
     * Returns a composed {@code LongQuadConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code LongQuadConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default LongQuadConsumer andThen(LongQuadConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4) -> {
            accept(x1, x2, x3, x4);
            after.accept(x1, x2, x3, x4);
        };
    }

}
