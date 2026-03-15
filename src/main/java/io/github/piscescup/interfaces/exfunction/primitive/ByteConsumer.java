package io.github.piscescup.interfaces.exfunction.primitive;

import io.github.piscescup.util.validation.NullCheck;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single {@code byte}-valued argument and returns no result.
 * This is the {@code byte}-producing primitive specialization for {@link Consumer}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose functional method is {@link #accept(byte)}.</p>
 *
 * @author REN YuanTong
 * @since 1.1.0
 */
public interface ByteConsumer {

    /**
     * Performs the operation on the given {@code byte}-valued argument.
     *
     * @param value the input argument
     */
    void accept(byte value);

    /**
     * Returns a composed {@code ByteConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code ByteConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ByteConsumer andThen(ByteConsumer after) {
        NullCheck.requireNonNull(after);
        return (byte t) -> {
            accept(t);
            after.accept(t);
        };
    }

}