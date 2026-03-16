package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts a single {@code char}-valued argument and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(char)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface CharConsumer {

    /**
     * Performs the operation on the given {@code char}-valued argument.
     *
     * @param value the input argument
     */
    void accept(char value);

    /**
     * Returns a composed {@code CharConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code CharConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default CharConsumer andThen(CharConsumer after) {
        NullCheck.requireNonNull(after);
        return (char t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
