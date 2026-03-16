package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts three {@code byte}-valued arguments and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(byte, byte, byte)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
@FunctionalInterface
public interface ByteTriConsumer {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     */
    void accept(byte x1, byte x2, byte x3);

    /**
     * Returns a composed {@code ByteTriConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ByteTriConsumer}
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default ByteTriConsumer andThen(ByteTriConsumer after) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3) -> {
            accept(x1, x2, x3);
            after.accept(x1, x2, x3);
        };
    }

}