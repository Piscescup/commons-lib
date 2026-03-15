package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single {@code float}-valued argument and returns no result.
 * This is the {@code float}-producing primitive specialization for {@link Consumer}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(float)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface FloatConsumer {

    /**
     * Performs the operation on the given {@code float}-valued argument.
     *
     * @param value the input argument
     */
    void accept(float value);

    /**
     * Returns a composed {@code FloatConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FloatConsumer}
     * @throws NullPointerException if {@code after} is null
     */
    default FloatConsumer andThen(FloatConsumer after) {
        NullCheck.requireNonNull(after);
        return (float t) -> {
            accept(t);
            after.accept(t);
        };
    }
}