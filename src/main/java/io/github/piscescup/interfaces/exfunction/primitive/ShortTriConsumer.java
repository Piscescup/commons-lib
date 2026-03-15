package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts three {@code short}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(short, short, short)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ShortTriConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     */
    void accept(short x1, short x2, short x3);

    /**
     * Returns a composed {@code ShortTriConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ShortTriConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default ShortTriConsumer andThen(ShortTriConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3) -> {
            accept(x1, x2, x3);
            after.accept(x1, x2, x3);
        };
    }

}