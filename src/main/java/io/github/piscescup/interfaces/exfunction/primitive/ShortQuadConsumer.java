package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts four {@code short}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(short, short, short, short)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortQuadConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     */
    void accept(short x1, short x2, short x3, short x4);

    /**
     * Returns a composed {@code ShortQuadConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ShortQuadConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default ShortQuadConsumer andThen(ShortQuadConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4) -> {
            accept(x1, x2, x3, x4);
            after.accept(x1, x2, x3, x4);
        };
    }

}
