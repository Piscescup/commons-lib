package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

/**
 * Represents an operation that accepts a single {@code boolean}-valued argument and returns no result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(boolean)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface BooleanConsumer {

    /**
     * Performs the operation on the given {@code boolean}-valued argument.
     *
     * @param value the input argument
     */
    void accept(boolean value);

    /**
     * Returns a composed {@code BooleanConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BooleanConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default BooleanConsumer andThen(BooleanConsumer after) {
        NullCheck.requireNonNull(after);
        return (boolean t) -> {
            accept(t);
            after.accept(t);
        };
    }
}