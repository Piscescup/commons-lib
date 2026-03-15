package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single {@code long}-valued argument and returns no result.
 * This is the {@code long}-producing primitive specialization for {@link Consumer}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(long)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface LongConsumer {

    /**
     * Performs the operation on the given {@code long}-valued argument.
     *
     * @param value the input argument
     */
    void accept(long value);

    /**
     * Returns a composed {@code LongConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code LongConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default LongConsumer andThen(LongConsumer after) {
        NullCheck.requireNonNull(after);
        return (long t) -> {
            accept(t);
            after.accept(t);
        };
    }
}