package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result, potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #acceptOrThrow(Object)}.
 * <p>
 * This interface is similar to {@link java.util.function.Consumer},
 * but allows the operation to throw a specified exception type.
 *
 * @param <X> the type of the input to the operation
 * @param <E> the type of exception that may be thrown by this consumer
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableConsumer<X, E extends Throwable> {

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     * @throws E if an error occurs during operation execution
     */
    void acceptOrThrow(X x) throws E;

    /**
     * Returns a composed {@code FailableConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     * <p>
     * If performing either operation throws an exception, it is relayed to
     * the caller of the composed operation. If this operation throws an
     * exception, the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FailableConsumer} that performs in sequence
     *         this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default FailableConsumer<X, E> andThen(
        FailableConsumer<? super X, E> after
    ) {
        NullCheck.requireNonNull(after);
        return x -> {
            acceptOrThrow(x);
            after.acceptOrThrow(x);
        };
    }

    /**
     * Returns a {@code FailableConsumer} that does not perform any operation on the input argument.
     *
     * @param <X> the type of the input to the operation
     * @param <E> the type of exception that may be thrown by this consumer, though in this case, no exception is expected to be thrown
     * @return a {@code FailableConsumer} which performs no operation
     */
    @Contract(pure = true)
    static <X, E extends Throwable> @NotNull FailableConsumer<X, E> empty() {
        return x -> {};
    }

    /**
     * Returns a {@code FailableConsumer} that is ensured to be non-null.
     *
     * @param <X> the type of the input to the operation
     * @param <E> the type of exception that may be thrown by this consumer
     * @param failableConsumer the failable consumer to check and return
     * @return the provided {@code FailableConsumer} if it is not null
     * @throws NullPointerException if the provided {@code failableConsumer} is null
     */
    static <X, E extends Throwable> FailableConsumer<X, E> of(
        FailableConsumer<X, E> failableConsumer
    ) {
        NullCheck.requireNonNull(failableConsumer);
        return failableConsumer;
    }

    /**
     * Narrows the type of the given failable consumer to a more specific type.
     *
     * @param <X> the type of the input to the operation
     * @param <E> the type of exception that may be thrown by this consumer
     * @param failableConsumer the failable consumer to narrow, must not be null
     * @return a failable consumer with its type narrowed to the specified type
     * @throws NullPointerException if the provided failableConsumer is null
     */
    @SuppressWarnings("unchecked")
    static <X, E extends Throwable> FailableConsumer<X, E> narrow(FailableConsumer<? super X, E> failableConsumer) {
        NullCheck.requireNonNull(failableConsumer);
        return (FailableConsumer<X, E>) failableConsumer;
    }
}
