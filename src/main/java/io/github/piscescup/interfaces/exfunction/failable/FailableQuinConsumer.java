package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.interfaces.exfunction.QuinConsumer;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * Represents an operation that accepts five input arguments and returns no result,
 * potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #acceptOrThrow(Object, Object, Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the operation
 * @param <X2> the type of the second argument to the operation
 * @param <X3> the type of the third argument to the operation
 * @param <X4> the type of the fourth argument to the operation
 * @param <X5> the type of the fifth argument to the operation
 * @param <E>  the type of exception that may be thrown by this operation
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableQuinConsumer<X1, X2, X3, X4, X5, E extends Throwable> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @param x4 the fourth input argument
     * @param x5 the fifth input argument
     * @throws E if an error occurs during operation execution
     */
    void acceptOrThrow(X1 x1, X2 x2, X3 x3, X4 x4, X5 x5) throws E;

    /**
     * Returns a composed {@code FailableQuinConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     * <p>
     * If this operation throws an exception, the {@code after} operation is not
     * performed.
     * <p>
     * If either operation throws an exception, it is relayed to the caller of
     * the composed operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FailableQuinConsumer} that performs in sequence
     *         this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default FailableQuinConsumer<X1, X2, X3, X4, X5, E> andThen(
        FailableQuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5, E> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            acceptOrThrow(x1, x2, x3, x4, x5);
            after.acceptOrThrow(x1, x2, x3, x4, x5);
        };
    }

    /**
     * Returns a composed {@code FailableQuinConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     * <p>
     * If this operation throws an exception, the {@code after} operation is not
     * performed.
     * <p>
     * If this operation throws an exception, it is relayed to the caller of
     * the composed operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FailableQuinConsumer} that performs in sequence
     *         this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default FailableQuinConsumer<X1, X2, X3, X4, X5, E> andThen(
        QuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3, x4, x5) -> {
            acceptOrThrow(x1, x2, x3, x4, x5);
            after.accept(x1, x2, x3, x4, x5);
        };
    }

    /**
     * Returns a {@code FailableQuinConsumer} that performs no operation.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <X4> the type of the fourth argument
     * @param <X5> the type of the fifth argument
     * @param <E>  the type of exception that may be thrown by the operation
     * @return a consumer that performs no operation
     */
    @Contract(pure = true)
    static <X1, X2, X3, X4, X5, E extends Throwable>
    @NotNull FailableQuinConsumer<X1, X2, X3, X4, X5, E> empty() {
        return (x1, x2, x3, x4, x5) -> {};
    }

    /**
     * Narrows a {@code FailableQuinConsumer} with broader generic bounds to a more
     * specific type.
     * <p>
     * This method performs an unchecked cast and is intended for use in situations
     * where the type safety is guaranteed by the caller.
     *
     * @param consumer the consumer to be narrowed
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <X4> the type of the fourth argument to the operation
     * @param <X5> the type of the fifth argument to the operation
     * @param <E>  the type of exception that may be thrown by the operation
     * @return the given consumer, narrowed to the desired generic type
     * @throws NullPointerException if {@code consumer} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, X4, X5, E extends Throwable>
    FailableQuinConsumer<X1, X2, X3, X4, X5, E> narrow(
        FailableQuinConsumer<? super X1, ? super X2, ? super X3, ? super X4, ? super X5, ? super E> consumer
    ) {
        NullCheck.requireNonNull(consumer);
        return (FailableQuinConsumer<X1, X2, X3, X4, X5, E>) consumer;
    }
}
