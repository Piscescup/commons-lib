package io.github.piscescup.interfaces.exfunction.failable;

import io.github.piscescup.interfaces.exfunction.TriConsumer;
import io.github.piscescup.util.validation.NullCheck;


/**
 * Represents an operation that accepts three input arguments and returns no result,
 * potentially throwing an exception.
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is
 * {@link #acceptOrThrow(Object, Object, Object)}.
 *
 * @param <X1> the type of the first argument to the operation
 * @param <X2> the type of the second argument to the operation
 * @param <X3> the type of the third argument to the operation
 * @param <E>  the type of exception that may be thrown by this operation
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface FailableTriConsumer<X1, X2, X3, E extends Throwable> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param x1 the first input argument
     * @param x2 the second input argument
     * @param x3 the third input argument
     * @throws E if an error occurs during operation execution
     */
    void acceptOrThrow(X1 x1, X2 x2, X3 x3) throws E;

    /**
     * Returns a composed {@code FailableTriConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     * <p>
     * If this operation throws an exception, the {@code after} operation is not
     * performed.
     * <p>
     * If either operation throws an exception, it is relayed to the caller of
     * the composed operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FailableTriConsumer} that performs in sequence
     *         this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default FailableTriConsumer<X1, X2, X3, E> andThen(
        FailableTriConsumer<? super X1, ? super X2, ? super X3, E> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3) -> {
            this.acceptOrThrow(x1, x2, x3);
            after.acceptOrThrow(x1, x2, x3);
        };
    }

    /**
     * Returns a composed {@code FailableTriConsumer} that performs, in sequence,
     * this operation followed by the {@code after} operation.
     * <p>
     * If this operation throws an exception, the {@code after} operation is not
     * performed.
     * <p>
     * If this operation throws an exception, it is relayed to the caller of
     * the composed operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code FailableTriConsumer} that performs in sequence
     *         this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default FailableTriConsumer<X1, X2, X3, E> andThen(
        TriConsumer<? super X1, ? super X2, ? super X3> after
    ) {
        NullCheck.requireNonNull(after);
        return (x1, x2, x3) -> {
            this.acceptOrThrow(x1, x2, x3);
            after.accept(x1, x2, x3);
        };
    }

    /**
     * Returns a {@code FailableTriConsumer} that performs no operation.
     *
     * @param <X1> the type of the first argument
     * @param <X2> the type of the second argument
     * @param <X3> the type of the third argument
     * @param <E>  the type of exception that may be thrown by the operation
     * @return a consumer that performs no operation
     */
    static <X1, X2, X3, E extends Throwable> FailableTriConsumer<X1, X2, X3, E> empty() {
        return (x1, x2, x3) -> {};
    }

    /**
     * Narrows a {@code FailableTriConsumer} with broader generic bounds to a more
     * specific type.
     * <p>
     * This method performs an unchecked cast and is intended for use in situations
     * where the type safety is guaranteed by the caller.
     *
     * @param consumer the consumer to be narrowed
     * @param <X1> the type of the first argument to the operation
     * @param <X2> the type of the second argument to the operation
     * @param <X3> the type of the third argument to the operation
     * @param <E>  the type of exception that may be thrown by the operation
     * @return the given consumer, narrowed to the desired generic type
     * @throws NullPointerException if {@code consumer} is {@code null}
     */
    @SuppressWarnings("unchecked")
    static <X1, X2, X3, E extends Throwable> FailableTriConsumer<X1, X2, X3, E> narrow(
        FailableTriConsumer<? super X1, ? super X2, ? super X3, ? super E> consumer
    ) {
        NullCheck.requireNonNull(consumer);
        return (FailableTriConsumer<X1, X2, X3, E>) consumer;
    }
}
