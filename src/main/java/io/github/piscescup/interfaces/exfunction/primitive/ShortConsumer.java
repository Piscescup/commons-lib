package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single {@code short}-valued argument and returns no result.
 * This is the {@code short}-producing primitive specialization for {@link Consumer}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(short)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface ShortConsumer {

    /**
     * Performs the operation on the given {@code short}-valued argument.
     *
     * @param value the input argument
     */
    void accept(short value);

    /**
     * Returns a composed {@code ShortConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ShortConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default ShortConsumer andThen(ShortConsumer after) {
        NullCheck.requireNonNull(after);
        return (short t) -> {
            accept(t);
            after.accept(t);
        };
    }
}